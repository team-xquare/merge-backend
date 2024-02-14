package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.entity.Deploy
import com.example.mergebackend.domain.deploy.entity.vo.DeployStatus
import com.example.mergebackend.domain.deploy.exception.AlreadyExistsDeployException
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.WebhookRequest
import com.example.mergebackend.domain.deploy.presentation.dto.response.CreateDeployResponse
import com.example.mergebackend.domain.deploy.repository.DeployRepository
import com.example.mergebackend.domain.project.exception.ProjectNotFoundException
import com.example.mergebackend.domain.project.repository.ProjectRepository
import com.example.mergebackend.global.common.facade.UserFacade
import com.example.mergebackend.infra.deploy.DeployClient
import com.example.mergebackend.infra.deploy.dto.FeignCreateDeployRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

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
        val organization = extractOrganization(createDeployRequest.githubUrl)
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
                repository = getRepository(deploy.githubUrl),
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

    private fun extractOrganization(githubUrl: String): String {
        val parts = githubUrl.split("/")
        return if (parts.size >= 4) {
            parts[3]
        } else {
            ""
        }
    }

    private fun getRepository(githubUrl: String): String {
        val parts = githubUrl.split("/")
        return if (parts.size >= 4) {
            "${parts[3]}/${parts[4]}"
        } else {
            ""
        }
    }
}