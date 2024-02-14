package com.example.mergebackend.global.config.feign.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException


object FeignForbiddenException : BusinessException(ErrorCode.FEIGN_FORBIDDEN_ERROR) {
    private fun readResolve(): Any = FeignForbiddenException
}
