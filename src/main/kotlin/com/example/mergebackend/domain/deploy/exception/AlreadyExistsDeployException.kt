package com.example.mergebackend.domain.deploy.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object AlreadyExistsDeployException : BusinessException(ErrorCode.ALREADY_EXISTS_DEPLOY)