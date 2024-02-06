package com.example.mergebackend.infra.vault.exception

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException

object VaultException : BusinessException(ErrorCode.VAULT_SERVER_ERROR)