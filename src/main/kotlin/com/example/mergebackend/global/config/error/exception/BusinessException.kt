package com.example.mergebackend.global.config.error.exception

import com.example.mergebackend.global.config.error.data.ErrorCode

open class BusinessException(val errorCode: ErrorCode): RuntimeException(errorCode.message)