package com.example.mergebackend.domain.user.service

import com.example.mergebackend.domain.user.presentation.dto.response.UserProfileResponse

interface UserService {

    fun getProfile(): UserProfileResponse
}