package com.example.mergebackend.global.config.kubernetes

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.eks.AmazonEKSClient
import com.amazonaws.services.eks.model.DescribeClusterRequest
import com.example.mergebackend.global.env.kubernetes.XquareAwsProperty
import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CustomObjectsApi
import io.kubernetes.client.util.Config
import org.springframework.context.annotation.Bean
import javax.annotation.PostConstruct


@org.springframework.context.annotation.Configuration
class KubernetesClientConfig(
    private val xquareAwsProperty: XquareAwsProperty
) {
    @PostConstruct
    fun initKubernetesConfig() {
        val credentialsProvider: AWSCredentialsProvider = AWSStaticCredentialsProvider(
            BasicAWSCredentials(xquareAwsProperty.accessKey, xquareAwsProperty.secretKey)
        )
        val eksClient = AmazonEKSClient.builder().withCredentials(credentialsProvider).build()

        val request = DescribeClusterRequest().withName("xquare-v2-cluster")
        val response = eksClient.describeCluster(request)
        val cluster = response.cluster

        val client: ApiClient = Config.fromToken(
            cluster.endpoint,
            cluster.certificateAuthority.data
        )
        Configuration.setDefaultApiClient(client)
    }

    @Bean
    fun customObjectsApi() = CustomObjectsApi()
}