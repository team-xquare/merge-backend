package com.example.mergebackend.domain.deploy.entity.vo

enum class DeployStatus {
    PENDING_APPROVE,
    APPROVED,
    CONTAINER_PENDING,
    CONTAINER_RUNNING,
    CONTAINER_CRASHLOOPBACKOFF,
    CONTAINER_EVICT
}