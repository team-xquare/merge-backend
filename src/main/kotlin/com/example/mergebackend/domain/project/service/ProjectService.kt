package com.example.mergebackend.domain.project.service

import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.infra.feign.oauth.dto.request.RegisterClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.request.UpdateClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.response.RegenerateSecretResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.RegisterClientResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.UpdateClientResponse
import org.springframework.web.multipart.MultipartFile
import java.util.*

interface ProjectService {

    fun register(req: RegisterProjectRequest, logo: MultipartFile, projectImage: List<MultipartFile>?): ProjectDetailResponse
    fun update(projectId: UUID, req: UpdateProjectRequest, logo: MultipartFile, projectImage: List<MultipartFile>?): ProjectDetailResponse
    fun getDetail(id: UUID): ProjectDetailResponse
    fun getProject(email: String): List<ProjectListResponse>
    fun getList(): List<ProjectListResponse>
    fun duplicate(projectNamEn: String): Boolean
    fun hide(projectId: UUID)
    fun unhide(projectId: UUID)
//    fun registerClient(req: RegisterClientRequest): RegisterClientResponse
//    fun updateClient(clientId: String, req: UpdateClientRequest): UpdateClientResponse
//    fun regenerateSecret(clientId: String): RegenerateSecretResponse

}