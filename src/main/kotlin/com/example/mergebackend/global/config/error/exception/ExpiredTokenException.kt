package com.example.mergebackend.global.config.error.exception

import com.example.mergebackend.global.config.error.data.ErrorCode

object ExpiredTokenException: BusinessException(ErrorCode.EXPIRED_TOKEN)