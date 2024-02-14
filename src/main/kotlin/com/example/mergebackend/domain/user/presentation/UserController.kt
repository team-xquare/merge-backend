package com.example.mergebackend.domain.user.presentation

import com.example.mergebackend.domain.user.presentation.dto.request.UpdateProfileRequest
import com.example.mergebackend.domain.user.presentation.dto.response.UserProfileResponse
import com.example.mergebackend.domain.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/myInfo")
    fun getProfile(): UserProfileResponse = userService.getProfile()

    @PutMapping("/update")
    fun updateProfile(
        @RequestBody @Valid
        req: UpdateProfileRequest
    ): UserProfileResponse = userService.updateProfile(req)

}