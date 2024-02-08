package com.example.mergebackend.domain.project.service

import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import java.util.*

interface ProjectService {

    fun register(req: RegisterProjectRequest): ProjectDetailResponse

    fun update(req: UpdateProjectRequest): ProjectDetailResponse

    fun getDetail(id: UUID): ProjectDetailResponse

    fun getProject(email: String): List<ProjectListResponse>

    fun getList(): List<ProjectListResponse>

}