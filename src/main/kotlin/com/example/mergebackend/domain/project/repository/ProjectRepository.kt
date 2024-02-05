package com.example.mergebackend.domain.project.repository

import com.example.mergebackend.domain.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProjectRepository: JpaRepository<Project, UUID>{

}