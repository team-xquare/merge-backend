package com.example.mergebackend.domain.user.presentation.dto.request

import javax.validation.constraints.NotBlank

data class UpdateProfileRequest (

    @field:NotBlank(message = "null이 될 수 없습니다.")
    val github: String
)