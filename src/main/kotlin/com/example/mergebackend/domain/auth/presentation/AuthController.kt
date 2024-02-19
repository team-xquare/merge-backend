package com.example.mergebackend.domain.auth.presentation

import com.example.mergebackend.domain.auth.presentation.dto.request.LoginRequest
import com.example.mergebackend.domain.auth.presentation.dto.request.ReissueRequest
import com.example.mergebackend.domain.auth.presentation.dto.request.SignUpRequest
import com.example.mergebackend.domain.auth.presentation.dto.response.TokenResponse
import com.example.mergebackend.domain.auth.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
        private val authService: AuthService
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(
            @RequestBody @Valid
            req: LoginRequest
    ): TokenResponse = authService.login(req)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
            @RequestBody @Valid
            request: SignUpRequest
    ) {
        authService.signup(request)
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    fun reissue(
            @RequestBody @Valid
            request: ReissueRequest
    ): TokenResponse = authService.reissue(request)

    @GetMapping("/duplicate")
    fun duplicateAccountId(
        @RequestParam("accountId") accountId: String
    ): Boolean = authService.duplicate(accountId)
}