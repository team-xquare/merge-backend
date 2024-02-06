package com.example.mergebackend.domain.deploy.presentation

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateEnvironmentVariableRequest
import com.example.mergebackend.domain.deploy.service.EnvironmentVariableService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/deploy/environment-variable")
class EnvironmentVariableController(
    private val environmentVariableService: EnvironmentVariableService
) {
    @PostMapping
    fun createEnvironmentVariable(
        @RequestBody @Valid
        createEnvironmentVariableRequest: CreateEnvironmentVariableRequest
    ) {
        environmentVariableService.create(createEnvironmentVariableRequest)
    }
}