package com.example.mergebackend.domain.user.service

import com.example.mergebackend.domain.project.entity.Project
import com.example.mergebackend.domain.user.entity.User
import com.example.mergebackend.domain.user.presentation.dto.request.UpdateProfileRequest
import com.example.mergebackend.domain.user.presentation.dto.response.UserProfileResponse
import com.example.mergebackend.domain.user.repository.UserRepository
import com.example.mergebackend.global.common.facade.UserFacade
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userFacade: UserFacade,
    private val userRepository: UserRepository
): UserService {

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()

    @Transactional
    override fun updateProfile(req: UpdateProfileRequest): UserProfileResponse {
        val currentUser = userFacade.getCurrentUser()

        req.github?.let {
            currentUser.updateGithub(it)
        }

        return userRepository.save(currentUser).toResponse()
    }

}