package com.example.mergebackend.domain.user.service

import com.example.mergebackend.domain.user.presentation.dto.request.UpdateProfileRequest
import com.example.mergebackend.domain.user.presentation.dto.response.UserProfileResponse
import com.example.mergebackend.domain.user.repository.UserRepository
import com.example.mergebackend.global.common.facade.UserFacade
import com.example.mergebackend.infra.feign.user.UserClient
import com.example.mergebackend.infra.feign.user.dto.UserInformationDto
import com.example.mergebackend.infra.feign.user.dto.response.TokenResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userFacade: UserFacade,
    private val userRepository: UserRepository,
    private val userClient: UserClient
): UserService {

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()

    @Transactional
    override fun updateProfile(req: UpdateProfileRequest): UserProfileResponse {
        val currentUser = userFacade.getCurrentUser()

        req.github.let {
            currentUser.updateGithub(it)
        }

        return userRepository.save(currentUser).toResponse()
    }

    @Transactional
    override fun reissue(refreshToken: String): TokenResponse {
        return userClient.reissue(refreshToken)
    }

    @Transactional
    override fun getUserByAccountId(accountId: String): UserInformationDto {
        return userClient.getUserByAccountId(accountId)
    }

    @Transactional
    override fun getUserByUserId(userId: UUID): UserInformationDto {
        return userClient.getUserByUserId(userId)
    }

}