package com.example.mergebackend.global.config.feign.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object FeignDefaultException : BusinessException(ErrorCode.FEIGN_SERVER_ERROR) {
    private fun readResolve(): Any = FeignDefaultException
}
