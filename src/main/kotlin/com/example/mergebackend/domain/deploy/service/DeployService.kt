package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.WebhookRequest
import java.util.UUID

interface DeployService {
    fun createDeploy(createDeployRequest: CreateDeployRequest)
    fun regenerateAccessKey(deployId: UUID)
    fun getClub(deployId: UUID)
    fun verifyAndGetRole(projectName: String, repository: String, accessKey: String, projectType: String)
    fun deployApprove(deployId: UUID)
    fun containerRestartWebhook(webhookRequest: WebhookRequest)
}