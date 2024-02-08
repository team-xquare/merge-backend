package com.example.mergebackend.domain.file.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object ImageDeleteFailException:BusinessException(ErrorCode.IMAGE_DELETE_FAIL)