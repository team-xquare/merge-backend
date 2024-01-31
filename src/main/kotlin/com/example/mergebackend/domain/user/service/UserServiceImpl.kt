package com.example.mergebackend.domain.user.service

import com.example.mergebackend.domain.user.entity.User
import com.example.mergebackend.domain.user.presentation.dto.request.UpdateProfileRequest
import com.example.mergebackend.domain.user.presentation.dto.response.UserProfileResponse
import com.example.mergebackend.domain.user.repository.UserRepository
import com.example.mergebackend.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
        private val userFacade: UserFacade,
        private val userRepository: UserRepository
): UserService {

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()

    @Transactional
    override fun updateProfile(req: UpdateProfileRequest): UserProfileResponse {
        val user = userFacade.getCurrentUser()

        val newUser = userRepository.save(User(
            user.id,
            req.studentName!!,
            user.password,
            req.schoolGcn!!,
            req.github!!
        ))

        return newUser.toResponse()
    }
}