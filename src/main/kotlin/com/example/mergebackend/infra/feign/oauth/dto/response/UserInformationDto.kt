package com.example.mergebackend.infra.feign.oauth.dto

import java.time.LocalDate
import java.util.*

data class UserInformationDto(
    val id: UUID,
    val accountId: String,
    val name: String,
    val birthday: LocalDate,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val profileFileName: String?,
    val password: String,
    val userRole: UserRole
)