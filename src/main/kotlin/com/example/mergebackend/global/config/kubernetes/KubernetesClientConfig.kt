package com.example.mergebackend.global.config.kubernetes

import com.example.mergebackend.global.env.kubernetes.XquareAwsProperty
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CustomObjectsApi
import io.kubernetes.client.util.Config
import org.springframework.context.annotation.Bean
import java.io.File
import java.io.FileWriter
import javax.annotation.PostConstruct


@org.springframework.context.annotation.Configuration
class KubernetesClientConfig(
    private val xquareAwsProperty: XquareAwsProperty
) {
    @PostConstruct
    fun initKubernetesConfig() {
        createAWSConfigFile()
        val client = Config.defaultClient()
        Configuration.setDefaultApiClient(client)
    }

    private fun createAWSConfigFile(){
        val path = System.getProperty("user.dir")
        val dirName = ".aws"

        val dir = File(path, dirName)
        if(!dir.exists()) dir.mkdir()

        val fileName = "credentials"
        val file = File("$path/$dirName", fileName)
        val writer = FileWriter(file)
        writer.write("[default]\n");
        writer.write("aws_access_key_id = " + xquareAwsProperty.accessKey + "\n");
        writer.write("aws_secret_access_key = " + xquareAwsProperty.secretKey + "\n")
        writer.close()

        println(xquareAwsProperty.accessKey + "\n")
        println(xquareAwsProperty.secretKey + "\n")
    }

    @Bean
    fun customObjectsApi() = CustomObjectsApi()
}