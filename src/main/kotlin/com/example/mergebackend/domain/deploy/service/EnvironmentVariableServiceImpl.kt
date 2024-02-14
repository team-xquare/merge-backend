package com.example.mergebackend.domain.deploy.service

import com.example.mergebackend.domain.deploy.entity.EnvironmentVariable
import com.example.mergebackend.domain.deploy.exception.AlreadyExistsEnvironmentVariable
import com.example.mergebackend.domain.deploy.exception.DeployNotFoundException
import com.example.mergebackend.domain.deploy.exception.EnvironmentVariableNotFound
import com.example.mergebackend.domain.deploy.presentation.dto.request.CreateEnvironmentVariableRequest
import com.example.mergebackend.domain.deploy.presentation.dto.request.UpdateEnvironmentVariableRequest
import com.example.mergebackend.domain.deploy.repository.DeployRepository
import com.example.mergebackend.domain.deploy.repository.EnvironmentVariableRepository
import com.example.mergebackend.domain.project.exception.ProjectNotFoundException
import com.example.mergebackend.infra.kubernetes.KubernetesClientUtil
import com.example.mergebackend.infra.vault.VaultUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
private class EnvironmentVariableServiceImpl(
    private val environmentVariableRepository: EnvironmentVariableRepository,
    private val deployRepository: DeployRepository,
    private val vaultUtil: VaultUtil,
    private val kubernetesClientUtil: KubernetesClientUtil
): EnvironmentVariableService{
    override fun create(req: CreateEnvironmentVariableRequest) {
        val deploy = deployRepository.findByIdOrNull(req.deployId) ?: throw DeployNotFoundException

        if(environmentVariableRepository.existsByDeploy(deploy)) {
            throw AlreadyExistsEnvironmentVariable
        }

        val environmentVariable = environmentVariableRepository.save(
            req.run {
                EnvironmentVariable(
                    deploy = deploy,
                    envType = envType!!,
                    variableList = variableList!!
                )
            }
        )
        val path = req.run { "${deploy.project.projectNameEn.lowercase()}-${deploy.serviceType.toString().lowercase()}-${envType.toString().lowercase()}" }
        vaultUtil.addSecret(req.variableList!!, path)

        val namespace = "${environmentVariable.deploy.project.teamNameEn.lowercase()}-${environmentVariable.envType.toString().lowercase()}"
        kubernetesClientUtil.deleteSecret(namespace, path)
    }

    override fun update(req: UpdateEnvironmentVariableRequest) {
        val deploy = deployRepository.findByIdOrNull(req.projectId) ?: throw DeployNotFoundException
        val environmentVariable = environmentVariableRepository.findByDeploy(deploy) ?: throw EnvironmentVariableNotFound
        environmentVariable.updateVariableList(req.variableList!!)
    }
}