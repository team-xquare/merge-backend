package com.example.mergebackend.domain.deploy.repository

import com.example.mergebackend.domain.deploy.entity.Deploy
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DeployRepository : JpaRepository<Deploy, UUID>