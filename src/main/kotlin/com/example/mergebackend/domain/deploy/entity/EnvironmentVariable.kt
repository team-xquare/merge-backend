package com.example.mergebackend.domain.deploy.entity

import com.example.mergebackend.domain.deploy.entity.type.EnvType
import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.project.entity.Project
import javax.persistence.*

@Entity(name = "environment_variable")
class EnvironmentVariable(
    id: Long? = null,
    project: Project,
    envType: EnvType,
    serviceType: ServiceType,
    variableList: Map<String, String>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = id
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var project: Project = project
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
    @MapKeyColumn(name="variable_key", length=100)
    @Column(name = "variable_value", columnDefinition = "TEXT")
    var variableList: Map<String, String> = variableList
        protected set

    fun updateVariableList(variableList: Map<String, String>) {
        this.variableList = variableList
    }
}