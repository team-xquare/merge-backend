package com.example.mergebackend.domain.project.presentation.dto.response

import java.time.LocalDate
import java.util.*

data class ProjectDetailResponse(
        val id: UUID,
        val logo: String,
        val studentName: String,
        val teamNameEn: String,
        val projectName: String,
        val projectNameEn: String,
        val description: String,
        val appStoreUrl: String?,
        val playStoreUrl: String?,
        val githubUrl: String?,
        val webUrl: String?,
        val projectImage: List<String>?,
        val isManagedByMe: Boolean,
        val date: LocalDate,
        val isHidden: Boolean
)