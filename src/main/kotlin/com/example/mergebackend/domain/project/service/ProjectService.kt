package com.example.mergebackend.domain.project.service

import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface ProjectService {

    fun register(req: RegisterProjectRequest, logo: MultipartFile, projectImage: List<MultipartFile>): ProjectDetailResponse
    fun update(projectId: UUID, req: UpdateProjectRequest): ProjectDetailResponse
    fun getDetail(id: UUID): ProjectDetailResponse
    fun getProject(email: String): List<ProjectListResponse>
    fun getList(): List<ProjectListResponse>

}