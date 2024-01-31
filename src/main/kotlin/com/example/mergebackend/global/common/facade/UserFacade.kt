package com.example.mergebackend.global.common.facade

import com.example.mergebackend.domain.user.entity.User
import com.example.mergebackend.domain.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {
    fun getCurrentUser(): User {
        val user = userRepository.findBySchoolGcn(SecurityContextHolder.getContext().authentication.name)
        return user ?: throw RuntimeException("User not found") //TODO 예외처리
    }
}