package com.example.mergebackend.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.DynamicUpdate
import java.util.*

@Entity(name = "user")
@DynamicUpdate
class User (
        id: UUID? = null,
        studentName: String,
        password: String,
        schoolGcn: String,
        github: String
) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16")
    var id: UUID? = id
        protected set

    @Column(name = "studentName", columnDefinition = "CHAR(4)", nullable = false)
    var studentName: String = studentName
        protected set

    @Column(name = "password", columnDefinition = "CHAR(60)", nullable = false)
    var password: String = password
        protected set

    @Column(name = "shoolGcn", columnDefinition = "CHAR(4)", nullable = false)
    var schoolGcn: String = schoolGcn
        protected set

    @Column(name = "github", columnDefinition = "VARCHAR(100)")
    var github: String = github
        protected set

}