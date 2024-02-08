package com.example.mergebackend.global.config.kubernetes

import io.kubernetes.client.openapi.apis.CustomObjectsApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct


@Configuration
class KubernetesClientConfig {
    @PostConstruct
    fun initKubernetesConfig() {
        val apiClient = io.kubernetes.client.util.Config.defaultClient()
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(apiClient)
    }

    @Bean
    fun customObjectsApi() = CustomObjectsApi()
}