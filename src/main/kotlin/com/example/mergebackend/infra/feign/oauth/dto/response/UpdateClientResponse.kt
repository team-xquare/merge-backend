package com.example.mergebackend.infra.feign.oauth.dto.response

class UpdateClientResponse (
    val clientId: String,
    val redirectUris: List<String>
)