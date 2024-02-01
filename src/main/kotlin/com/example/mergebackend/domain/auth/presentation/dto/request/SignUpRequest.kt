package com.example.mergebackend.domain.auth.presentation.dto.request

import jakarta.validation.constraints.*

data class SignUpRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Size(min = 2, max = 4, message = "이름은 2자 ~ 4자입니다.")
    val studentName: String?,

    @field:NotBlank(message = "null이 될 없습니다.")
    @field:Pattern(
            regexp = "^(http|https)://github.com",
            message = "올바른 github 주소 형식이 아닙니다."
    )
    val github: String?,

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Pattern(
            regexp = "^[a-zA-z]{4,}\\d{4,}[!@#%&()*~]",
            message = "비밀번호는 영문 대소문자 4자 이상, 숫자 4자 이상, 특수문자 1자 이상이며 총 15자 이하여야 합니다."
    )
    val password: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Size(min = 4, max = 4, message = "학번은 4자리입니다.")
    var schoolGcn: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Pattern(
            regexp = ".*@dsm.hs.kr.*",
            message = "올바른 이메일 형식이 아닙니다."
    )
    var email: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Size(min = 5, max = 15, message = "아이디는 최소 5자, 최대 15자 입니다.")
    var accountId: String?
)