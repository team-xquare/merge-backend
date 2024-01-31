package com.example.mergebackend.domain.user.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateProfileRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Size(min = 2, max = 4, message = "이름은 2자 ~ 4자입니다.")
    val studentName: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Size(min = 4, max = 4, message = "학번은 4자리입니다.")
    var schoolGcn: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    var github: String?


)
