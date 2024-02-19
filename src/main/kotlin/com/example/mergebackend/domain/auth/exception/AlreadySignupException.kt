package com.example.mergebackend.domain.auth.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object AlreadySignupException: BusinessException(ErrorCode.ALREADY_SIGN_UP)