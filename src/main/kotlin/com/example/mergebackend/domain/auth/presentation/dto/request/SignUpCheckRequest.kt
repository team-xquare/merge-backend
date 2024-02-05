package com.example.mergebackend.domain.auth.presentation.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class SignUpCheckRequest(

        @field:NotNull(message = "null이 될 수 없습니다.")
        @field:Pattern(
                regexp = "",
                message = "올바른 이메일 형식이 아닙니다."
        )
        val email: String?,

        @field:NotNull(message = "null이 될 수 없습니다.")
        @field:Max(999999, message = "올바른 인증 번호를 입력해 주세요.")
        @field:Min(100000, message = "올바른 인증 번호를 입력해 주세요.")
        val number: Int?
)