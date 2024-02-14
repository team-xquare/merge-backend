package com.example.mergebackend.domain.deploy.presentation

import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.service.DeployService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/deploy")
@RestController
class DeployController(
    private val deployService: DeployService
) {
    @PostMapping
    fun createDeploy(
        @RequestBody
        createDeployRequest: CreateDeployRequest
    ) {
        deployService.createDeploy(createDeployRequest)
    }

}