package com.example.mergebackend.domain.deploy.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object DeployNotFoundException : BusinessException(ErrorCode.DEPLOY_NOT_FOUND_EXCEPTION)