package com.example.mergebackend.domain.deploy.presentation

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateEnvironmentVariableRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.UpdateEnvironmentVariableRequest
import com.example.mergebackend.domain.deploy.service.EnvironmentVariableService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/deploy/environment-variable")
class EnvironmentVariableController(
    private val environmentVariableService: EnvironmentVariableService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createEnvironmentVariable(
        @RequestBody @Valid
        createEnvironmentVariableRequest: CreateEnvironmentVariableRequest
    ) {
        environmentVariableService.create(createEnvironmentVariableRequest)
    }

    @PutMapping
    fun updateEnvironmentVariable(
        @RequestBody @Valid
        updateEnvironmentVariableRequest: UpdateEnvironmentVariableRequest
    ) {
        environmentVariableService.update(updateEnvironmentVariableRequest)
    }
}