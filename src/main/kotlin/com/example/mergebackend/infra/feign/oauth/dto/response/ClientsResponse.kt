package com.example.mergebackend.infra.feign.oauth.dto.response

data class ClientsResponse (
    val clients: List<ClientResponse>
) {
    data class ClientResponse(
        val clientId: String,
        val redirectUris: List<String>
    )
}