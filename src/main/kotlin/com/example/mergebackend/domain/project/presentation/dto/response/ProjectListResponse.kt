package com.example.mergebackend.domain.project.presentation.dto.response

import java.time.LocalDate
import java.util.*

data class ProjectListResponse(
        val id: UUID,
        val projectNameKo: String,
        val teamNameEn: String,
        val logo: String,
        val date: LocalDate,
        val isHidden: Boolean
)