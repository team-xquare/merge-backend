package com.example.mergebackend.infra.feign.oauth

import com.example.mergebackend.global.config.feign.FeignConfig
import com.example.mergebackend.infra.feign.oauth.dto.request.RegisterClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.request.UpdateClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.response.ClientsResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.RegenerateSecretResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.RegisterClientResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.UpdateClientResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "oauthClient",
    url = "\${BASE_URL}",
    configuration = [FeignConfig::class]
)
interface OAuthClient {

    @PostMapping("/oauth2/client")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun registerClient(
        @RequestHeader("")
        @RequestBody request: RegisterClientRequest
    ): RegisterClientResponse

    @PatchMapping("/oauth2/client/{client-id}")
    fun updateClient(
        @PathVariable("client-id") clientId: String,
        @RequestBody request: UpdateClientRequest
    ): UpdateClientResponse

    @GetMapping("/oauth2/client/{client-id}/secret")
    fun regenerateSecret(@PathVariable("client-id") clientId: String): RegenerateSecretResponse

}