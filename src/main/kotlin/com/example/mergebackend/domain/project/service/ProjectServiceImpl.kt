package com.example.mergebackend.domain.project.service

import com.example.mergebackend.domain.file.service.FileService
import com.example.mergebackend.domain.project.entity.Project
import com.example.mergebackend.domain.project.exception.AlreadyExistException
import com.example.mergebackend.domain.project.exception.ProjectNotFoundException
import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.domain.project.repository.ProjectRepository
import com.example.mergebackend.domain.user.exception.UserNotFoundException
import com.example.mergebackend.domain.user.repository.UserRepository
import com.example.mergebackend.global.common.facade.UserFacade
//import com.example.mergebackend.infra.feign.oauth.OAuthClient
import com.example.mergebackend.infra.feign.oauth.dto.request.RegisterClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.request.UpdateClientRequest
import com.example.mergebackend.infra.feign.oauth.dto.response.RegenerateSecretResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.RegisterClientResponse
import com.example.mergebackend.infra.feign.oauth.dto.response.UpdateClientResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.*

@Service
@Transactional(readOnly = true)
class ProjectServiceImpl(
    private val projectRepository: ProjectRepository,
    private val userFacade: UserFacade,
    private val fileService: FileService,
    private val userRepository: UserRepository,
//    private val oAuthClient: OAuthClient
) : ProjectService {

    @Transactional
    override fun register(
        req: RegisterProjectRequest,
        logo: MultipartFile,
        projectImage: List<MultipartFile>?
    ): ProjectDetailResponse {
        val user = userFacade.getCurrentUser()

        val duplicateProject = projectRepository.findByProjectNameEn(req.projectNameEn)

        if (duplicateProject != null) {
            throw AlreadyExistException
        }

        val logoUrl = logo.let { fileService.upload(it, req.projectNameEn).url } ?: ""
        val projectImageUrls = projectImage?.let {
            if (it.isNotEmpty()) {
                fileService.uploads(it, req.projectNameEn).files.map { fileResponse -> fileResponse.url }
            } else {
                null
            }
        }

        val project = Project(
            null,
            user,
            logoUrl,
            req.projectName,
            req.projectNameEn,
            req.teamNameEn,
            req.description,
            req.githubUrl,
            req.webUrl,
            req.playStoreUrl,
            req.appStoreUrl,
            projectImageUrls,
            date = LocalDate.now()
        )

        projectRepository.save(project)


        return project.toResponse(user)
    }


    @Transactional
    override fun update(
        projectId: UUID,
        req: UpdateProjectRequest,
        logo: MultipartFile,
        projectImage: List<MultipartFile>?
    ): ProjectDetailResponse {

        val user = userFacade.getCurrentUser()
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException

        val logoUrl = fileService.upload(logo, project.projectNameEn).url

        val projectImageUrl = projectImage?.let {
            if (it.isNotEmpty()) {
                fileService.uploads(it, project.projectNameEn).files.map { fileUrlResponse -> fileUrlResponse.url }
            } else {
                project.projectImage
            }
        }

        return projectRepository.save(
            Project(
                projectId,
                user,
                logoUrl,
                project.projectName,
                project.projectNameEn,
                project.teamNameEn,
                req.description,
                req.githubUrl,
                req.webUrl,
                req.playStoreUrl,
                req.appStoreUrl,
                projectImageUrl,
                project.date
            )
        ).toResponse(user)
    }

    @Transactional
    override fun getDetail(id: UUID): ProjectDetailResponse {

        val project = projectRepository.findByIdOrNull(id)
            ?: throw ProjectNotFoundException

        val user = userFacade.getCurrentUserOrNull()

        return project.toResponse(user)
    }

    @Transactional
    override fun getProject(email: String): List<ProjectListResponse> {
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException

        val projects = projectRepository.findByUser(user)

        if (projects.isEmpty())
            throw ProjectNotFoundException

        return projects.map { it.toListResponse() }
    }

    @Transactional
    override fun getList(): List<ProjectListResponse> {
        val projects = projectRepository.findAll()

        return projects.map { it.toListResponse() }
    }

    @Transactional
    override fun duplicate(projectNamEn: String): Boolean {
        val duplicateProject = projectRepository.findByProjectNameEn(projectNamEn)

        return duplicateProject != null
    }

    @Transactional
    override fun hide(projectId: UUID) {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException

        project.isHidden = true
    }

    @Transactional
    override fun unhide(projectId: UUID) {
        val project = projectRepository.findByIdOrNull(projectId)
            ?: throw ProjectNotFoundException

        project.isHidden = false
    }
//
//    @Transactional
//    override fun registerClient(req: RegisterClientRequest): RegisterClientResponse {
//        return oAuthClient.registerClient(
//            request = RegisterClientRequest(
//                clientId = req.clientId,
//                redirectUris = req.redirectUris ?: emptyList()
//            )
//        )
//    }
//
//    @Transactional
//    override fun updateClient(clientId: String, req: UpdateClientRequest): UpdateClientResponse {
//        return oAuthClient.updateClient(clientId, req)
//    }
//
//    @Transactional
//    override fun regenerateSecret(clientId: String): RegenerateSecretResponse {
//        return oAuthClient.regenerateSecret(clientId)
//    }
}