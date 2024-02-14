package com.example.mergebackend.domain.deploy.entity

import com.example.mergebackend.domain.deploy.entity.type.ServiceType
import com.example.mergebackend.domain.deploy.entity.vo.DeployStatus
import com.example.mergebackend.domain.deploy.entity.vo.UseDatabase
import com.example.mergebackend.domain.project.entity.Project
import java.util.UUID
import javax.persistence.*

@Entity(name = "deploy")
class Deploy(
    id: UUID? = null,
    project: Project,
    accessKey: String,
    serviceType: ServiceType,
    organization: String,
    useDatabase: UseDatabase,
    isApproved: Boolean,
    deployStatus: DeployStatus
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var project: Project = project
        protected set

    @Column(name = "access_key", nullable = false)
    var accessKey: String = accessKey
        protected set

    @Enumerated(EnumType.STRING)
    var serviceType: ServiceType = serviceType
        protected set

    @Column(name = "organization", nullable = false)
    var organization: String = organization
        protected set

    @ElementCollection
    var useDatabase: UseDatabase = useDatabase
        protected set

    @Column(name = "is_approved", nullable = false)
    var isApproved: Boolean = isApproved
        protected set

    @Enumerated(EnumType.STRING)
    var deployStatus: DeployStatus = deployStatus
        protected set
}