package com.example.mergebackend.domain.deploy.presentation.dto.response

data class DeployResponse(
    val containerName: String,
    val repository: String,
    val lastDeploy: String,
    val url: Map<String, String>
)
