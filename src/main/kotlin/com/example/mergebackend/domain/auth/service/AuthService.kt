package com.example.mergebackend.domain.auth.service

import com.example.mergebackend.domain.auth.presentation.dto.request.LoginRequest
import com.example.mergebackend.domain.auth.presentation.dto.request.ReissueRequest
import com.example.mergebackend.domain.auth.presentation.dto.request.SignUpRequest
import com.example.mergebackend.domain.auth.presentation.dto.response.TokenResponse

interface AuthService {
    fun login(req: LoginRequest): TokenResponse
    fun signup(req: SignUpRequest)
    fun reissue(req: ReissueRequest): TokenResponse
}