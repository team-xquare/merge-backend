package com.example.mergebackend.domain.file.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object InvalidFileExtension: BusinessException(ErrorCode.INVALID_FILE_EXTENSION)