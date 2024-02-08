package com.example.mergebackend.infra.kubernetes

import com.example.mergebackend.infra.kubernetes.exception.KubernetesException
import io.kubernetes.client.openapi.apis.CustomObjectsApi
import io.kubernetes.client.openapi.models.V1DeleteOptions
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class KubernetesClientUtilImpl(
    private val customObjectsApi: CustomObjectsApi
): KubernetesClientUtil {
    override fun deleteSecret(namespace: String, crName: String) {
        val v1DeleteOption = V1DeleteOptions()
        try {
            customObjectsApi.deleteNamespacedCustomObject(
                "secrets.hashicorp.com",
                "v1beta1",
                namespace,
                "vaultdynamicsecrets",
                crName,
                null,
                null,
                null,
                null,
                v1DeleteOption
            )
        } catch (e: Exception) {
            throw KubernetesException
        }
    }
}