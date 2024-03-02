package com.example.mergebackend.infra.feign.user

import com.example.mergebackend.global.config.feign.FeignConfig
import com.example.mergebackend.infra.feign.user.dto.UserInformationDto
import com.example.mergebackend.infra.feign.user.dto.response.TokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader
import java.util.*

@FeignClient(
    name = "userClient",
    url = "\${BASE_URL}",
    configuration = [FeignConfig::class]
)
interface UserClient {

    @PutMapping("/users/login")
    fun reissue(@RequestHeader("Authorization") refreshToken: String): TokenResponse

    @GetMapping("/users/account-id/{accountId}")
    fun getUserByAccountId(@PathVariable("accountId") accountId: String): UserInformationDto

    @GetMapping("/users/id/{userId}")
    fun getUserByUserId(@PathVariable("userId") userId: UUID): UserInformationDto
}