package com.example.mergebackend.domain.deploy.presentation

import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateDeployRequest
import com.example.mergebackend.domain.deploy.presentation.dto.response.CreateDeployResponse
import com.example.mergebackend.domain.deploy.presentation.dto.response.DeployListResponse
import com.example.mergebackend.domain.deploy.service.DeployService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import java.util.Base64
import java.util.UUID
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

    @GetMapping("/duplicate")
    fun duplicateCheck(
        @RequestParam("container-name")
        containerName: String
    ): Boolean {
        return deployService.duplicateCheck(containerName)
    }

    @PutMapping("/config")
    fun updateConfig(
        @RequestParam(value = "container_name", required = true)
        containerName: String,
        @RequestParam(value = "service_type", required = true)
        serviceType: ServiceType,
        @RequestParam(value = "prefix", required = false)
        prefix: String?,
        @RequestParam(value = "domain", required = false)
        domain: String?
    ) {
        if (domain != null && domain.isNotEmpty()) {
            val decodedDomain = Base64.getDecoder().decode(domain)
            val domainMap = String(decodedDomain, Charset.defaultCharset()).lines().associate {
                val (key, value) = it.split(":").map { it.trim() }
                key to value
            }
            deployService.updateUrl(containerName, serviceType, prefix, domainMap)
        } else {
            deployService.updateUrl(containerName, serviceType, prefix, null)
        }
    }

    @GetMapping("/all")
    fun getAllDeployByProject(
        @RequestParam("project_id")
        projectId: UUID
    ): DeployListResponse = deployService.getDeployList(projectId)
}