package com.example.mergebackend.domain.deploy.repository

import com.example.mergebackend.domain.deploy.entity.Deploy
import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DeployRepository : JpaRepository<Deploy, UUID> {
    fun existsByContainerName(containerName: String): Boolean
}