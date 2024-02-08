package com.example.mergebackend.domain.project.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object AlreadyExistException:BusinessException(ErrorCode.ALREADY_EXIST)