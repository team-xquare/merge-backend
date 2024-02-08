package com.example.mergebackend.domain.project.service

import com.example.mergebackend.domain.file.service.FileService
import com.example.mergebackend.domain.project.entity.Project
import com.example.mergebackend.domain.project.exception.ProjectNotFoundException
import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.domain.project.repository.ProjectRepository
import com.example.mergebackend.domain.user.exception.UserNotFoundException
import com.example.mergebackend.domain.user.repository.UserRepository
import com.example.mergebackend.global.common.facade.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class ProjectServiceImpl (
        private val projectRepository: ProjectRepository,
        private val userFacade: UserFacade,
        private val fileService: FileService, private val userRepository: UserRepository
): ProjectService {

    @Transactional
    override fun register(req: RegisterProjectRequest): ProjectDetailResponse {
        val user = userFacade.getCurrentUser()
        val logoUrl = req.logo?.let { fileService.upload(req.logo, req.projectNameEn).url } ?: ""
        val projectImageUrls = req.projectImage?.map {
            fileService.upload(it, req.projectNameEn).url
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
                projectImageUrls
        )

        projectRepository.save(project)

        return project.toResponse()
    }


    @Transactional
    override fun update(projectId: UUID, req: UpdateProjectRequest): ProjectDetailResponse {

        val user = userFacade.getCurrentUser()
        val project = projectRepository.findByIdOrNull(projectId)
                ?: throw ProjectNotFoundException

        val logoUrl = fileService.upload(req.logo, project.projectNameEn).url

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
                project.projectImage
        )).toResponse()
    }

    @Transactional
    override fun getDetail(id: UUID): ProjectDetailResponse {

        val project = projectRepository.findByIdOrNull(id)
                ?: throw ProjectNotFoundException

        return project.toResponse()
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
}