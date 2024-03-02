package com.example.mergebackend.domain.user.service

import com.example.mergebackend.domain.user.presentation.dto.request.UpdateProfileRequest
import com.example.mergebackend.domain.user.presentation.dto.response.UserProfileResponse
import com.example.mergebackend.infra.feign.user.dto.UserInformationDto
import com.example.mergebackend.infra.feign.user.dto.response.TokenResponse
import java.util.*

interface UserService {

    fun getProfile(): UserProfileResponse
    fun updateProfile(req: UpdateProfileRequest): UserProfileResponse
    fun reissue(refreshToken: String): TokenResponse
    fun getUserByAccountId(accountId: String): UserInformationDto
    fun getUserByUserId(userId: UUID): UserInformationDto
}