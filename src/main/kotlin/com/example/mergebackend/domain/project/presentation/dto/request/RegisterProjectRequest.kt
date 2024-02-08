package com.example.mergebackend.domain.project.presentation.dto.request

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class RegisterProjectRequest(

        val logo: MultipartFile,

        @field:NotBlank(message = "null이 될 수 없습니다.")
        val projectNameKo: String,

        @field:NotBlank(message = "null이 될 수 없습니다.")
        val projectNameEn: String,

        @field:NotBlank(message = "null이 될 수 없습니다.")
        val teamNameEn: String,

        @field:NotBlank(message = "null이 될 수 없습니다.")
        val description: String,

        @field:Pattern(
                regexp = "^https://github\\.com/.+",
                message = "올바른 깃허브 링크가 아닙니다."
        )
        val githubUrl: String?,

        @field:Pattern(
                regexp = "^(http|https)://",
                message = "올바른 웹주소 형식이 아닙니다."
        )
        val webUrl: String?,

        @field:Pattern(
                regexp = "^https://play\\.google\\.com/store/apps/details\\?id=[a-zA-Z0-9._-]+\$\n",
                message = "올바른 플레이스토어 주소가 아닙니다."
        )
        val playStoreUrl: String?,

        @field:Pattern(
                regexp = "^https://apps\\.apple\\.com/[a-z]{2}/app/[a-zA-Z0-9._-]+/[a-zA-Z0-9._-]+\$\n",
                message = "올바른 앱스토어 주소가 아닙니다."
        )
        val appStoreUrl: String?,

        val projectImage : List<MultipartFile>?
)