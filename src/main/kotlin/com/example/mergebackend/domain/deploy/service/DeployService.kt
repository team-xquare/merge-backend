package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.WebhookRequest
import com.example.mergebackend.domain.deploy.presentation.dto.response.CreateDeployResponse
import java.util.UUID

interface DeployService {
    fun createDeploy(createDeployRequest: CreateDeployRequest): CreateDeployResponse
    fun regenerateAccessKey(deployId: UUID)
    fun duplicateCheck(containerName: String): Boolean
}