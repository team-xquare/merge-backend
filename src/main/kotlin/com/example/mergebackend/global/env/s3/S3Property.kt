package com.example.mergebackend.global.env.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("cloud.aws.s3")
@ConstructorBinding
data class S3Property(
        val bucket: String,
        val dir: String,
        val accessKey: String,
        val secretKey: String,
        val region: String
)