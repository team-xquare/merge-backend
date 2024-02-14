package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.entity.Deploy
import com.example.mergebackend.domain.deploy.entity.vo.DeployStatus
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.WebhookRequest
import com.example.mergebackend.domain.deploy.repository.DeployRepository
import com.example.mergebackend.domain.project.exception.ProjectNotFoundException
import com.example.mergebackend.domain.project.repository.ProjectRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
private class DeployServiceImpl(
    private val deployRepository: DeployRepository,
    private val projectRepository: ProjectRepository
): DeployService {
    override fun createDeploy(createDeployRequest: CreateDeployRequest) {
        val project = projectRepository.findByIdOrNull(createDeployRequest.projectId) ?: throw ProjectNotFoundException

        val organization = extractOrganization(createDeployRequest.githubUrl)
        deployRepository.save(
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