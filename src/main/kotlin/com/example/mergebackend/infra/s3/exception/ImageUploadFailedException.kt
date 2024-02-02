package com.example.mergebackend.infra.s3.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object ImageUploadFailedException: BusinessException(ErrorCode.IMAGE_UPLOAD_FAILED)