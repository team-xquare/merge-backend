package com.example.mergebackend.infra.feign.tsdata

import com.example.mergebackend.global.config.feign.FeignConfig
import com.example.mergebackend.infra.feign.deploy.dto.FeignCreateDeployRequest
import com.example.mergebackend.infra.feign.tsdata.dto.GetLogRequest
import com.example.mergebackend.infra.feign.tsdata.dto.LogResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "tsdata-client",
    url = "\${url.tsdata}",
    configuration = [FeignConfig::class]
)
interface TsDataClient {
    @PostMapping("/api/ds/query")
    fun getLogs(@RequestBody getLogRequest: GetLogRequest): LogResponse
}