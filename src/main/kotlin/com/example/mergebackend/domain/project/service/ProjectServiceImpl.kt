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
import com.example.mergebackend.infra.vault.VaultUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.util.*

@Service
@Transactional(readOnly = true)
class ProjectServiceImpl (
        private val projectRepository: ProjectRepository,
        private val userFacade: UserFacade,
        private val fileService: FileService,
        private val userRepository: UserRepository,
        private val vaultUtil: VaultUtil
): ProjectService {

    @Transactional
    override fun register(req: RegisterProjectRequest, logo: MultipartFile, projectImage: List<MultipartFile>?): ProjectDetailResponse {
        val user = userFacade.getCurrentUser()

        val logoUrl = logo.let { fileService.upload(it, req.projectNameEn).url } ?: ""
        val projectImageUrls = projectImage?.let {
            fileService.uploads(it, req.projectNameEn).files.map { fileResponse -> fileResponse.url }
        } ?: emptyList()

        val project = Project(
            null,
            user,
            logoUrl,
            req.projectNameKo,
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

        val paths = listOf(
            "${project.projectNameEn}-be-stag",
            "${project.projectNameEn}-be-prod"
        )

        paths.forEach { path ->
            vaultUtil.addSecret(mapOf("initKey" to "initValue"), path)
        }
        return project.toResponse(user)
    }


    @Transactional
    override fun update(projectId: UUID, req: UpdateProjectRequest, logo: MultipartFile): ProjectDetailResponse {

        val user = userFacade.getCurrentUser()
        val project = projectRepository.findByIdOrNull(projectId)
                ?: throw ProjectNotFoundException

        val logoUrl = fileService.upload(logo, project.projectNameEn).url

        return projectRepository.save(Project(
            projectId,
            user,
            logoUrl,
            project.projectNameKo,
            project.projectNameEn,
            project.teamNameEn,
            req.description,
            req.githubUrl,
            req.webUrl,
            req.playStoreUrl,
            req.appStoreUrl,
            project.projectImage,
            project.date
        )).toResponse(user)
    }

    @Transactional
    override fun getDetail(id: UUID): ProjectDetailResponse {

        val project = projectRepository.findByIdOrNull(id)
                ?: throw ProjectNotFoundException

        val user = userFacade.getCurrentUser()

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
}