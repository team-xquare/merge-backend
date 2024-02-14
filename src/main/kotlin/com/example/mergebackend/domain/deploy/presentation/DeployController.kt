package com.example.mergebackend.domain.deploy.presentation

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.response.CreateDeployResponse
import com.example.mergebackend.domain.deploy.service.DeployService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RequestMapping("/deploy")
@RestController
class DeployController(
    private val deployService: DeployService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createDeploy(
        @RequestBody @Valid
        createDeployRequest: CreateDeployRequest
    ): CreateDeployResponse {
        return deployService.createDeploy(createDeployRequest)
    }

}