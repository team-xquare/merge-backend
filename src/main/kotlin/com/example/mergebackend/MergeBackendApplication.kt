package com.example.mergebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
@ConfigurationPropertiesScan
@SpringBootApplication
class MergeBackendApplication

fun main(args: Array<String>) {
    runApplication<MergeBackendApplication>(*args)
}
