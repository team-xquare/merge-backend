package com.example.mergebackend.domain.project.presentation

import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.domain.project.service.ProjectService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Validated
@RestController
@RequestMapping("/project")
class ProjectController(
        private val projectService: ProjectService,
        private val objectMapper: ObjectMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
            @RequestPart("logo", required = true) logo: MultipartFile,
            @RequestPart("projectImage") projectImage: List<MultipartFile>,
            @RequestPart("project") project: String
    ): ProjectDetailResponse {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        mapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        val request: RegisterProjectRequest = mapper.readValue(project, RegisterProjectRequest::class.java)
        return projectService.register(request, logo, projectImage)
    }

    @PutMapping("/{projectId}")
    fun update(
            @PathVariable("projectId") projectId: UUID,
            @RequestPart(value = "logo", required = true) logo: MultipartFile,
            @RequestPart("project") project: String
    ): ProjectDetailResponse {
        val request: UpdateProjectRequest = objectMapper.readValue(project, UpdateProjectRequest::class.java)
        return projectService.update(projectId, request, logo)
    }

    @GetMapping("/detail")
    fun getProjectDetail(
            @RequestParam("id") id: UUID
    ): ProjectDetailResponse = projectService.getDetail(id)

    @GetMapping("/user")
    fun getProjectByUser(
            @RequestParam("email") email: String
    ): List<ProjectListResponse> = projectService.getProject(email)

    @GetMapping("/list")
    fun getList(): List<ProjectListResponse> = projectService.getList()

    @GetMapping("/duplicate")
    fun duplicateProject(
        @RequestParam("projectNameEn") projectNameEn: String
    ): Boolean = projectService.duplicate(projectNameEn)
}