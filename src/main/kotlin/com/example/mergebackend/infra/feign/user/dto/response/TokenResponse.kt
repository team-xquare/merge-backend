package com.example.mergebackend.infra.feign.user.dto.response

import com.example.mergebackend.infra.feign.oauth.dto.UserRole
import java.time.LocalDateTime

data class TokenResponse (
    val accessToken: String,
    val accessTokenExpireAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpireAt: LocalDateTime,
    val userRole: UserRole
)