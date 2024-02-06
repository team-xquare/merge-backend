package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateEnvironmentVariableRequest


interface EnvironmentVariableService {
    fun create(req: CreateEnvironmentVariableRequest)
}