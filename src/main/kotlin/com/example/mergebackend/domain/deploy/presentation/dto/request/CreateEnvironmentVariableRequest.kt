package com.example.mergebackend.domain.deploy.presentation.dto.request

import com.example.mergebackend.domain.deploy.entity.type.EnvType
import com.example.mergebackend.domain.deploy.entity.type.ServiceType

data class CreateEnvironmentVariableRequest(
    val projectName: String,
    val envType: EnvType,
    val serviceType: ServiceType,
    val variableList: Map<String, String>
)
