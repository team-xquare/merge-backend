package com.example.mergebackend.domain.deploy.service.impl

import com.example.mergebackend.domain.deploy.entity.Deploy
import com.example.mergebackend.domain.deploy.entity.type.EnvType
import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.deploy.entity.vo.DeployStatus
import com.example.mergebackend.domain.deploy.exception.AlreadyExistsDeployException
import com.example.mergebackend.domain.deploy.exception.DeployNotFoundException
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.response.CreateDeployResponse
import com.example.mergebackend.domain.deploy.presentation.dto.response.DeployListResponse
import com.example.mergebackend.domain.deploy.presentation.dto.response.DeployLogResponse
import com.example.mergebackend.domain.deploy.presentation.dto.response.DeployResponse
import com.example.mergebackend.domain.deploy.repository.DeployRepository
import com.example.mergebackend.domain.deploy.service.DeployService
import com.example.mergebackend.domain.deploy.service.util.DeployUtil
import com.example.mergebackend.domain.project.exception.ProjectNotFoundException
import com.example.mergebackend.domain.project.repository.ProjectRepository
import com.example.mergebackend.global.common.facade.UserFacade
import com.example.mergebackend.infra.feign.deploy.DeployClient
import com.example.mergebackend.infra.feign.deploy.dto.FeignCreateDeployRequest
import com.example.mergebackend.infra.feign.tsdata.TsDataClient
import com.example.mergebackend.infra.feign.tsdata.dto.GetLogRequest
import com.example.mergebackend.infra.feign.tsdata.dto.QueryDto
import com.example.mergebackend.infra.kubernetes.KubernetesClientUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Transactional
@Service
private class DeployServiceImpl(
    private val deployRepository: DeployRepository,
    private val projectRepository: ProjectRepository,
    private val deployClient: DeployClient,
    private val userFacade: UserFacade,
    private val tsDataClient: TsDataClient,
    private val kubernetesClientUtil: KubernetesClientUtil
): DeployService {
    override fun createDeploy(createDeployRequest: CreateDeployRequest): CreateDeployResponse {
        val project = projectRepository.findByIdOrNull(createDeployRequest.projectId) ?: throw ProjectNotFoundException

        if(deployRepository.existsByContainerName(createDeployRequest.containerName)) {
            throw AlreadyExistsDeployException
        }
        val organization = DeployUtil.extractOrganization(createDeployRequest.githubUrl)
        val deploy = deployRepository.save(
            createDeployRequest.run {
                Deploy(
                    project = project,
                    accessKey = "",
                    serviceType = serviceType,
                    organization = organization,
                    useDatabase = useDatabase,
                    isApproved = false,
                    deployStatus = DeployStatus.PENDING_APPROVE,
                    containerName = containerName,
                    githubUrl = githubUrl
                )
            }
        )

        val user = userFacade.getCurrentUser()
        deployClient.createDeploy(
            FeignCreateDeployRequest(
                email = user.email,
                nameKo = deploy.project.projectName,
                nameEn = deploy.containerName,
                team = deploy.project.teamNameEn,
                repository = DeployUtil.getRepository(deploy.githubUrl),
                organization = deploy.organization,
                type = deploy.serviceType.toString().lowercase(),
                useRedis = deploy.useDatabase.redis,
                useMySQL = deploy.useDatabase.mysql
            )
        )
        return CreateDeployResponse(deployId = deploy.id!!)
    }

    override fun regenerateAccessKey(deployId: UUID) {
        TODO("Not yet implemented")
    }

    override fun duplicateCheck(containerName: String): Boolean {
        return deployRepository.existsByContainerName(containerName)
    }

    override fun getDeployList(projectId: UUID): DeployListResponse {
        val project = projectRepository.findByIdOrNull(projectId) ?: throw ProjectNotFoundException
        val deployList = deployRepository.findAllByProject(project).map {
            DeployResponse(
                containerName = it.containerName,
                repository = DeployUtil.getRepository(it.githubUrl),
                lastDeploy = it.lastDeploy ?: "배포내역이 없습니다",
                url = it.deployUrl ?: mapOf("stag" to "배포내역이 존재하지 않습니다.", "prod" to "배포내역이 존재하지 않습니다.")
            )
        }
        return DeployListResponse(deployList)
    }

    override fun updateUrl(
        containerName: String,
        serviceType: ServiceType,
        prefix: String?,
        domain: Map<String, String>?
    ) {
        val deploy = deployRepository.findByContainerNameAndServiceType(containerName, serviceType) ?: throw DeployNotFoundException
        val deployUrl = mutableMapOf<String, String>()
        prefix?.let {
            deployUrl["stag"] = "https://stag-server.xquare.app$prefix"
            deployUrl["prod"] = "https://prod-server.xquare.app$prefix"
        }
        domain?.let {
            deployUrl["stag"] = "https://${domain["stag"]}"
            deployUrl["prod"] = "https://${domain["prod"]}"
        }
        deploy.updateDeployUrl(deployUrl)
    }

    override fun getLogs(deployId: UUID, envType: EnvType): DeployLogResponse {
        val deploy = deployRepository.findByIdOrNull(deployId) ?: throw DeployNotFoundException

        val currentTimeMillis = Instant.now().toEpochMilli()
        val twentyFourHoursAgoMillis = currentTimeMillis - (24 * 60 * 60 * 1000)

        val request = GetLogRequest(
            queries = listOf(
                QueryDto(
                    expr = DeployUtil.makeLogQuery(
                        team = deploy.project.teamNameEn,
                        containerName = deploy.containerName,
                        serviceType = deploy.serviceType,
                        envType = envType
                    ),
                    refId = "A",
                    datasource = "loki"
                )
            ),
            from = twentyFourHoursAgoMillis.toString(),
            to = currentTimeMillis.toString()
        )

        val response = tsDataClient.getLogs(request)

        return DeployLogResponse(response.results.a.frames[0].data.values[2])

    }

    override fun checkPodStatus(deployId: UUID): Map<String, String> {
        val deploy = deployRepository.findByIdOrNull(deployId) ?: throw DeployNotFoundException

        val namespacePrefix = "${deploy.project.teamNameEn}-"
        val deployPrefix = "${deploy.containerName}-${deploy.serviceType.toString().lowercase()}-"

        val stagStatus = kubernetesClientUtil.checkContainerStatus(namespace = "${namespacePrefix}stag", deploymentName = "${deployPrefix}stag")
        val prodStatus = kubernetesClientUtil.checkContainerStatus(namespace = "${namespacePrefix}prod", deploymentName = "${deployPrefix}prod")

        return mapOf(
            "stag" to stagStatus,
            "prod" to prodStatus
        )
    }
}