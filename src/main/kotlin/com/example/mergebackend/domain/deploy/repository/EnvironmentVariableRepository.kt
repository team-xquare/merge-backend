package com.example.mergebackend.domain.deploy.repository

import com.example.mergebackend.domain.deploy.entity.EnvironmentVariable
import com.example.mergebackend.domain.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository

interface EnvironmentVariableRepository : JpaRepository<EnvironmentVariable, Long> {
    fun existsByProject(project: Project): Boolean

    fun findByProject(project: Project): EnvironmentVariable?
}
