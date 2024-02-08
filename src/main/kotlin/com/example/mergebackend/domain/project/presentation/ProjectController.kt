package com.example.mergebackend.domain.project.presentation

import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.domain.project.service.ProjectService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/project")
class ProjectController(
        private val projectService: ProjectService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
            @RequestPart("logo", required = true) logo: MultipartFile,
            @RequestPart("project") project: String
    ): ProjectDetailResponse {
        val mapper = ObjectMapper()
        val request: RegisterProjectRequest = mapper.readValue(project, RegisterProjectRequest::class.java)
        val finalRequest = request.copy(logo = logo)
        return projectService.register(finalRequest)
    }

    @PutMapping("/{projectId}")
    fun update(
            @PathVariable("projectId") projectId: UUID,
            @RequestPart(value = "logo", required = true) logo: MultipartFile,
            @RequestPart("project") project: String
    ): ProjectDetailResponse {
        val mapper = ObjectMapper()
        val request: UpdateProjectRequest = mapper.readValue(project, UpdateProjectRequest::class.java)
        val finalRequest = request.copy(logo = logo)
        return projectService.update(projectId, finalRequest)
    }

    @GetMapping("/detail")
    fun getProjectDetail(
            @RequestParam("id") id: UUID
    ): ProjectDetailResponse = projectService.getDetail(id)

    @GetMapping("/user/{email}")
    fun getProjectByUser(
            @PathVariable("email") email: String
    ): List<ProjectListResponse> = projectService.getProject(email)

    @GetMapping("/list")
    fun getList(): List<ProjectListResponse> = projectService.getList()
}