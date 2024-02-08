package com.example.mergebackend.domain.project.presentation

import com.example.mergebackend.domain.project.presentation.dto.request.RegisterProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.request.UpdateProjectRequest
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.domain.project.service.ProjectService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
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
            @RequestBody @Valid
            req: RegisterProjectRequest
    ): ProjectDetailResponse = projectService.register(req)

    @PutMapping("/{projectId}")
    fun update(
            @PathVariable("projectId") projectId: UUID,
            @RequestBody @Valid
            req: UpdateProjectRequest
    ): ProjectDetailResponse = projectService.update(projectId, req)

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