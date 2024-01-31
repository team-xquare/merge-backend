package com.example.mergebackend.domain.user.presentation.dto.response

import java.util.*

data class UserProfileResponse (
    val id: UUID,
    val studentName: String,
    val schoolGcn: String,
    val github: String
)