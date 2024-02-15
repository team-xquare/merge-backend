package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.WebhookRequest
import com.example.mergebackend.domain.deploy.presentation.dto.response.CreateDeployResponse
import com.example.mergebackend.domain.deploy.presentation.dto.response.DeployListResponse
import java.util.UUID

interface DeployService {
    fun createDeploy(createDeployRequest: CreateDeployRequest): CreateDeployResponse
    fun regenerateAccessKey(deployId: UUID)
    fun duplicateCheck(containerName: String): Boolean
    fun getDeployList(projectId: UUID): DeployListResponse
    fun updateUrl(containerName: String, serviceType: ServiceType, prefix: String?, domain: Map<String,String>?)
}