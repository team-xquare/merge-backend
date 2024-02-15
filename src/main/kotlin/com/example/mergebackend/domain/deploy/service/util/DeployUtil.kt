package com.example.mergebackend.domain.deploy.service.util

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
}