package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateEnvironmentVariableRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.UpdateEnvironmentVariableRequest


interface EnvironmentVariableService {
    fun create(req: CreateEnvironmentVariableRequest)
    fun update(req: UpdateEnvironmentVariableRequest)
}