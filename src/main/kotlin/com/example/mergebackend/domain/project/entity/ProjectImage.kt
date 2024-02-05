package com.example.mergebackend.domain.project.entity

import java.util.*
import javax.persistence.*

@Entity(name = "project_image")
class ProjectImage(
        id: UUID? = null,
        projectImage: String?,
        project: Project
) {

    @Id @Column(name = "id")
    var id: UUID? = id
        protected set

    @Column(name = "projectImage", columnDefinition = "VARCHAR(200)")
    var projectImage: String? = projectImage
        protected set

    @ManyToOne
    @JoinColumn(name = "project_id")
    var project: Project = project
        protected set
}