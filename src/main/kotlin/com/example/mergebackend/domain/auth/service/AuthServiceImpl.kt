package com.example.mergebackend.domain.auth.service

import com.example.mergebackend.domain.auth.exception.PasswordNotMatchedException
import com.example.mergebackend.domain.auth.presentation.dto.request.LoginRequest
import com.example.mergebackend.domain.auth.presentation.dto.request.ReissueRequest
import com.example.mergebackend.domain.auth.presentation.dto.request.SignUpRequest
import com.example.mergebackend.domain.auth.presentation.dto.response.TokenResponse
import com.example.mergebackend.domain.user.entity.User
import com.example.mergebackend.domain.user.exception.UserNotFoundException
import com.example.mergebackend.domain.user.repository.UserRepository
import com.example.mergebackend.global.config.jwt.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val tokenProvider: TokenProvider
): AuthService {

    @Transactional
    override fun login(req: LoginRequest): TokenResponse {
        val user = userRepository.findByAccountId(req.accountId!!)
                ?: throw UserNotFoundException

        if (!passwordEncoder.matches(req.password, user.password)) throw PasswordNotMatchedException

        return tokenProvider.receiveToken(user.accountId)
    }

    @Transactional
    override fun signup(request: SignUpRequest) {

        userRepository.save(User(
                studentName = request.studentName!!,
                accountId = request.accountId!!,
                password = passwordEncoder.encode(request.password!!),
                schoolGcn = request.schoolGcn!!,
                email = request.email!!,
                github = request.github!!
        ))
    }

    @Transactional
    override fun reissue(req: ReissueRequest): TokenResponse = tokenProvider.reissue(req.refreshToken)

    @Transactional
    override fun duplicate(accountId: String): Boolean {
        val duplicateAccountId = userRepository.findByAccountId(accountId)

        return duplicateAccountId != null
    }
}