package com.example.mergebackend.domain.auth.presentation.dto.request

import javax.validation.constraints.NotNull

data class ReissueRequest(
        @field:NotNull
        val refreshToken: String
)