package com.example.mergebackend.domain.deploy.service.util

import com.example.mergebackend.domain.deploy.entity.type.EnvType
import com.example.mergebackend.domain.deploy.entity.type.ServiceType

object DeployUtil {
    fun extractOrganization(githubUrl: String): String {
        val parts = githubUrl.split("/")
        return if (parts.size >= 4) {
            parts[3]
        } else {
            ""
        }
    }

    fun getRepository(githubUrl: String): String {
        val parts = githubUrl.split("/")
        return if (parts.size >= 4) {
            "${parts[3]}/${parts[4]}"
        } else {
            ""
        }
    }

    fun makeLogQuery(team: String, containerName: String, serviceType: ServiceType, envType: EnvType): String {
        val fullName = "${containerName}-${serviceType.toString().lowercase()}-${envType.toString().lowercase()}"
        return "{job=\"$team-${envType.toString().lowercase()}/$fullName\", container=~\"$fullName\", stream=~\"stdout\"} |~ \"(?i)\" \n"
    }
}