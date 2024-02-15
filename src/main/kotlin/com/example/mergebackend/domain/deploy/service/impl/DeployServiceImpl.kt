package com.example.mergebackend.domain.deploy.service.impl

import com.example.mergebackend.domain.deploy.entity.Deploy
import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.deploy.entity.vo.DeployStatus
import com.example.mergebackend.domain.deploy.exception.AlreadyExistsDeployException
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.response.CreateDeployResponse
import com.example.mergebackend.domain.deploy.presentation.dto.response.DeployListResponse
import com.example.mergebackend.domain.deploy.presentation.dto.response.DeployResponse
import com.example.mergebackend.domain.deploy.repository.DeployRepository
import com.example.mergebackend.domain.deploy.service.DeployService
import com.example.mergebackend.domain.deploy.service.util.DeployUtil
import com.example.mergebackend.domain.project.exception.ProjectNotFoundException
import com.example.mergebackend.domain.project.repository.ProjectRepository
import com.example.mergebackend.global.common.facade.UserFacade
import com.example.mergebackend.infra.deploy.DeployClient
import com.example.mergebackend.infra.deploy.dto.FeignCreateDeployRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Service
private class DeployServiceImpl(
    private val deployRepository: DeployRepository,
    private val projectRepository: ProjectRepository,
    private val deployClient: DeployClient,
    private val userFacade: UserFacade
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
                nameKo = deploy.project.projectNameKo,
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
        val deploy = deployRepository.findByContainerNameAndServiceType(containerName, serviceType)
        val deployUrl = mapOf<String, String>()
        prefix?.let {
            deployUrl["stag"] to "https://stag-server.xquare.app/$prefix"
            deployUrl["prod"] to "https://prod-server.xquare.app/$prefix"
        }
        domain?.let {
            deployUrl["stag"] to "https://${domain["stag"]}"
            deployUrl["prod"] to "https://${domain["prod"]}"
        }
        deploy.updateDeployUrl(deployUrl)
    }
}