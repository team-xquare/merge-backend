package com.example.mergebackend.domain.auth.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object PasswordMismatchedException: BusinessException(ErrorCode.PASSWORD_NOT_MATCHED)