package com.example.mergebackend.global.config.error.exception

import com.example.mergebackend.global.config.error.data.ErrorCode

object InvalidTokenException: BusinessException(ErrorCode.INVALID_TOKEN)