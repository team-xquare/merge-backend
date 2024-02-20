package com.example.mergebackend.infra.kubernetes

interface KubernetesClientUtil {
    fun deleteSecret(namespace: String, crName: String)
    fun checkContainerStatus(deploymentName: String, namespace: String): String
}