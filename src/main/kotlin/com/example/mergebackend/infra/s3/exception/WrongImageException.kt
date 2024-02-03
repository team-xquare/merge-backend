package com.example.mergebackend.infra.s3.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object WrongImageException: BusinessException(ErrorCode.WRONG_IMAGE)
