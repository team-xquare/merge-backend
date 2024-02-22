package com.example.mergebackend.infra.feign.oauth.dto.request

data class UpdateClientRequest (
    val redirectUris: List<String>
)