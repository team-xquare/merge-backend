package com.example.mergebackend.infra.feign.oauth

import com.example.mergebackend.global.config.feign.FeignConfig
import com.example.mergebackend.infra.feign.oauth.dto.response.UserInformationDto
import com.example.mergebackend.infra.feign.oauth.dto.request.RegisterClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.request.UpdateClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.response.ClientsResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.RegenerateSecretResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.RegisterClientResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.UpdateClientResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*

@FeignClient(
    name = "oauthClient",
    url = "\${OAUTH_URL}",
    configuration = [FeignConfig::class]
)
interface OAuthClient {

    @GetMapping("/client")
    fun getClient(): ClientsResponse

    @PostMapping("/client")
    fun registerClient(@RequestBody request: RegisterClientRequest): RegisterClientResponse

    @PatchMapping("/client/{client-id}")
    fun updateClient(@PathVariable("client-id") clientId: String,@RequestBody request: UpdateClientRequest): UpdateClientResponse

    @GetMapping("/client/{client-id}/secret")
    fun regenerateSecret(@PathVariable("client-id") clientId: String): RegenerateSecretResponse

    @GetMapping("/userinfo/{account-id}")
    fun getUserInfo(@PathVariable("account-id") accountId: String): UserInformationDto
}