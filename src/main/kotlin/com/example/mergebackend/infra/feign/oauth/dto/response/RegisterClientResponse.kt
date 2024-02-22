package com.example.mergebackend.infra.feign.oauth.dto.response

data class RegisterClientResponse (
    val clientId: String,
    val clientSecret: String,
    val redirectUris: List<String>
)