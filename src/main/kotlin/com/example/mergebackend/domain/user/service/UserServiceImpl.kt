package com.example.mergebackend.domain.user.service

import com.example.mergebackend.domain.user.presentation.dto.response.UserProfileResponse
import com.example.mergebackend.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
        private val userFacade: UserFacade,
): UserService {

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()

}