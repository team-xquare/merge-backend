package com.example.mergebackend.domain.deploy.presentation.dto.request

import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.deploy.entity.vo.UseDatabase
import java.util.UUID

data class CreateDeployRequest(
    val containerName: String,
    val projectId: UUID,
    val serviceType: ServiceType,
    val useDatabase: UseDatabase
)
