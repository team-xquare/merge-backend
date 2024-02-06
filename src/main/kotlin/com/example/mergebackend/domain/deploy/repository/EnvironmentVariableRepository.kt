package com.example.mergebackend.domain.deploy.repository

import com.example.mergebackend.domain.deploy.entity.EnvironmentVariable
import org.springframework.data.jpa.repository.JpaRepository

interface EnvironmentVariableRepository : JpaRepository<EnvironmentVariable, Long>