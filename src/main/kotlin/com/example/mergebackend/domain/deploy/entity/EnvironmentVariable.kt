package com.example.mergebackend.domain.deploy.entity

import com.example.mergebackend.domain.deploy.entity.type.EnvType
import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import javax.persistence.*

@Entity(name = "environment_variable")
class EnvironmentVariable(
    id: Long? = null,
    projectName: String,
    envType: EnvType,
    serviceType: ServiceType,
    variableList: Map<String, String>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @Column(name = "project_name", nullable = false)
    var projectName: String = projectName
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "env_type", nullable = false)
    var envType: EnvType = envType
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    var serviceType: ServiceType = serviceType
        protected set

    @ElementCollection
    var variableList: Map<String, String> = variableList
        protected set
}