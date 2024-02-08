package com.example.mergebackend.global.config.kubernetes

import com.example.mergebackend.global.env.kubernetes.KubernetesProperty
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CustomObjectsApi
import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import org.springframework.context.annotation.Bean
import java.io.StringReader
import javax.annotation.PostConstruct


@org.springframework.context.annotation.Configuration
class KubernetesClientConfig(
    private val kubernetesProperty: KubernetesProperty
) {
    @PostConstruct
    fun initKubernetesConfig() {
        val client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(StringReader(kubernetesProperty.kubeConfig))).build()
        Configuration.setDefaultApiClient(client)
    }

    @Bean
    fun customObjectsApi() = CustomObjectsApi()
}