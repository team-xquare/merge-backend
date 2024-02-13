package com.example.mergebackend.global.config.kubernetes

import com.example.mergebackend.global.config.kubernetes.dto.KubernetesToken
import com.example.mergebackend.global.env.kubernetes.KubernetesProperty
import com.example.mergebackend.global.env.kubernetes.XquareAwsProperty
import com.example.mergebackend.infra.kubernetes.exception.KubernetesException
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CustomObjectsApi
import io.kubernetes.client.util.ClientBuilder
import io.kubernetes.client.util.KubeConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import software.amazon.awssdk.regions.Region
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.nio.charset.Charset
import java.util.*
import javax.annotation.PostConstruct


@org.springframework.context.annotation.Configuration
class KubernetesClientConfig(
    private val xquareAwsProperty: XquareAwsProperty,
    private val kubernetesProperty: KubernetesProperty
) {
    @PostConstruct
    fun initKubernetesConfig() {
        configureAWS("default", xquareAwsProperty.accessKey, xquareAwsProperty.secretKey, Region.AP_NORTHEAST_2.toString())
        val token = getEksToken("xquare-v2-cluster") ?: throw KubernetesException
        println(kubernetesProperty.kubeConfig)
        val decodedBytes = Base64.getDecoder().decode(kubernetesProperty.kubeConfig)
        val kubeconfig = String(decodedBytes, Charset.defaultCharset()) + " $token"

        println(kubeconfig)
        val client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(StringReader(kubeconfig))).build()
        Configuration.setDefaultApiClient(client)
    }

    private fun configureAWS(profileName: String, accessKeyId: String, secretAccessKey: String, region: String) {
        try {
            val processBuilder = ProcessBuilder()
            processBuilder.command("aws", "configure", "set", "aws_access_key_id", accessKeyId, "--profile", profileName)
            var process = processBuilder.start()
            process.waitFor()

            processBuilder.command("aws", "configure", "set", "aws_secret_access_key", secretAccessKey, "--profile", profileName)
            process = processBuilder.start()
            process.waitFor()

            processBuilder.command("aws", "configure", "set", "region", region, "--profile", profileName)
            process = processBuilder.start()
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getEksToken(clusterName: String): String? {
        try {
            val processBuilder = ProcessBuilder()
            processBuilder.command("aws", "eks", "get-token", "--cluster-name", clusterName)

            val process = processBuilder.start()
            val output = StringBuilder()

            val reader = BufferedReader(InputStreamReader(process.inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line + "\n")
            }

            val exitVal = process.waitFor()
            if (exitVal == 0) {
                val json = Json { ignoreUnknownKeys = true }
                return json.decodeFromString<KubernetesToken>(output.toString()).status.token
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @Bean
    fun customObjectsApi() = CustomObjectsApi()
}