package com.example.mergebackend.domain.deploy.presentation.dto.request

import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.deploy.entity.vo.UseDatabase
import java.util.UUID
import javax.validation.constraints.Pattern

data class CreateDeployRequest(
    val containerName: String,
    val projectId: UUID,
    val serviceType: ServiceType,
    val useDatabase: UseDatabase,
    @field:Pattern(
        regexp = "^https://github.com/[a-zA-Z0-9_-]+/[a-zA-Z0-9_-]+",
        message = "올바른 github 주소가 아닙니다"
    )
    val githubUrl: String
)
