package com.example.mergebackend.domain.project.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object ProjectNotFoundException: BusinessException(ErrorCode.USER_NOT_FOUND)