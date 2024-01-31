package com.example.mergebackend.domain.user.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object UserNotFoundException : BusinessException(ErrorCode.USER_NOT_FOUND) {
}