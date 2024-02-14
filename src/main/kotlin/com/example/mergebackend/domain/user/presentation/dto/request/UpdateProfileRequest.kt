package com.example.mergebackend.domain.user.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UpdateProfileRequest (

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^https://github\\.com/[A-z0-9_-]+(/)?([A-z0-9_-]+(/)?)?$",
        message = "올바른 Github 주소 형식이 아닙니다."
    )
    val github: String
)