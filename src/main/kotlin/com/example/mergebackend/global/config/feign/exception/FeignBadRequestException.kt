package com.example.mergebackend.global.config.feign.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object FeignBadRequestException : BusinessException(ErrorCode.FEIGN_BAD_REQUEST) {

    private fun readResolve(): Any = FeignBadRequestException
}
