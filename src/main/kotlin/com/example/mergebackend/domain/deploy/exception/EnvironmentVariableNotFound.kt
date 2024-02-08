package com.example.mergebackend.domain.deploy.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object EnvironmentVariableNotFound : BusinessException(ErrorCode.PROJECT_NOT_FOUND)