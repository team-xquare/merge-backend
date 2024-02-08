package com.example.mergebackend.domain.project.presentation.dto.response

import java.util.*

data class ProjectListResponse(
        val id: UUID,
        val projectNameEn: String,
        val teamNameEn: String,
        val logo: String
)