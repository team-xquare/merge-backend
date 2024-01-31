package com.example.mergebackend.global.common.facade

import com.example.mergebackend.domain.user.entity.User
import com.example.mergebackend.domain.user.repository.UserRepository
import com.example.mergebackend.global.config.error.exception.InvalidTokenException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {
    fun getCurrentUser(): User = userRepository.findBySchoolGcn(SecurityContextHolder.getContext().authentication.name)
                ?: throw InvalidTokenException
}