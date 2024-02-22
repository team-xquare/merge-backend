package com.example.mergebackend.infra.feign.oauth.dto.request

data class RegisterClientRequest(
    val clientId: String,
    val redirectUris: List<String>
)