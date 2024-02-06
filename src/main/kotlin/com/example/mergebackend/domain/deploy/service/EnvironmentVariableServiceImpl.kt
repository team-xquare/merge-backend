package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.entity.EnvironmentVariable
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateEnvironmentVariableRequest
import com.example.mergebackend.domain.deploy.repository.EnvironmentVariableRepository
import com.example.mergebackend.infra.vault.VaultUtil
import org.springframework.stereotype.Service

@Service
private class EnvironmentVariableServiceImpl(
    private val environmentVariableRepository: EnvironmentVariableRepository,
    private val vaultUtil: VaultUtil
): EnvironmentVariableService{

    override fun create(req: CreateEnvironmentVariableRequest) {
        environmentVariableRepository.save(
            req.run {
                EnvironmentVariable(
                    projectName = projectName,
                    envType = envType,
                    serviceType = serviceType,
                    variableList = variableList
                )
            }
        )
        val path = req.run { "${projectName.lowercase()}-${serviceType.toString().lowercase()}-${envType.toString().lowercase()}" }
        vaultUtil.addSecret(req.variableList, path)
    }
}