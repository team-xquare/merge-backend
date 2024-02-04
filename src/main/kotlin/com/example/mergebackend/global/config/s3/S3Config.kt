package com.example.mergebackend.global.config.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.example.mergebackend.global.env.s3.S3Property
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
        val s3Property: S3Property
) {

    @Bean
    fun amazonS3Client() = AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(s3Property.accessKey, s3Property.secretKey)))
            .withRegion(s3Property.region)
            .build() as AmazonS3Client
}