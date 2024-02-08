package com.example.mergebackend.domain.file.entity

import com.example.mergebackend.domain.project.entity.Project
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "file")
@DynamicUpdate
class File (
        id: UUID? = null,
        url: String,
        project: Project?
){
    @Id @Column(name = "id")
    var id: UUID? = id
        protected set

    @Column(name = "url", columnDefinition = "VARCHAR(255)")
    var url: String = url
        protected set

    @ManyToOne
    @JoinColumn(name = "project_id")
    var project: Project? = null
        protected set
}