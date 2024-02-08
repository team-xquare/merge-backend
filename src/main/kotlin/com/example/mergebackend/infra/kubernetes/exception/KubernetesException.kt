package com.example.mergebackend.infra.kubernetes.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object KubernetesException : BusinessException(ErrorCode.KUBERNETES_SERVER_ERROR)