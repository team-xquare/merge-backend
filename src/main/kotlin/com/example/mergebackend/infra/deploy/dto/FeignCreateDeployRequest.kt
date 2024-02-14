package com.example.mergebackend.infra.deploy.dto

data class FeignCreateDeployRequest(
    val email: String,
    val nameKo: String,
    val nameEn: String,
    val team: String,
    val repository: String,
    val organization: String,
    val type: String,
    val useRedis: Boolean,
    val useMysSQL: Boolean
)
