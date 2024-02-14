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
        if(deployRepository.existsByProjectAndServiceType(project, createDeployRequest.serviceType)) {
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
                nameEn = deploy.project.projectNameEn,
                team = deploy.project.teamNameEn,
                repository = deploy.githubUrl,
                organization = deploy.organization,
                type = deploy.serviceType.toString(),
                useRedis = deploy.useDatabase.redis,
                useMysSQL = deploy.useDatabase.mysql
            )
        )
        return CreateDeployResponse(deployId = deploy.id!!)
    }

    override fun regenerateAccessKey(deployId: UUID) {
        TODO("Not yet implemented")
    }

    override fun getClub(deployId: UUID) {
        TODO("Not yet implemented")
    }

    override fun verifyAndGetRole(projectName: String, repository: String, accessKey: String, projectType: String) {
        TODO("Not yet implemented")
    }

    override fun deployApprove(deployId: UUID) {
        TODO("Not yet implemented")
    }

    override fun containerRestartWebhook(webhookRequest: WebhookRequest) {
        TODO("Not yet implemented")
    }

    private fun extractOrganization(githubUrl: String): String {
        val parts = githubUrl.split("/")
        return if (parts.size >= 4) {
            parts[3]
        } else {
            ""
        }
    }
}