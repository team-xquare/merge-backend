package com.example.mergebackend.domain.auth.presentation.dto.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class SignUpEmailRequest(
        @field:NotNull(message = "null이 될 수 없습니다.")
        @field:Pattern(
                regexp = "",
                message = "올바른 이메일 형식이 아닙니다."
        )
        val email: String?
)