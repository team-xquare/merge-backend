package com.example.mergebackend.domain.user.repository

import com.example.mergebackend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, UUID?> {

    fun existsBySchoolGcn(schoolGcn: String): Boolean
    fun findByAccountId(accountId: String): User?

    fun findBySchoolGcn(schoolGcn: String): User?
}