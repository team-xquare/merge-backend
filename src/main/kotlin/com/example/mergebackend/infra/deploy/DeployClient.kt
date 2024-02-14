package com.example.mergebackend.infra.deploy

import com.example.mergebackend.global.config.feign.FeignConfig
import com.example.mergebackend.infra.deploy.dto.FeignCreateDeployRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "deploy-client",
    url = "\${url.deploy}",
    configuration = [FeignConfig::class]
)
interface DeployClient {
    @PostMapping("/project")
    fun createDeploy(@RequestBody feignCreateDeployRequest: FeignCreateDeployRequest)
}