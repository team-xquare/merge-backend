package com.example.mergebackend.domain.deploy.entity.vo

import javax.persistence.Embeddable

@Embeddable
data class UseDatabase(
    var mysql: Boolean,
    var redis: Boolean
)
