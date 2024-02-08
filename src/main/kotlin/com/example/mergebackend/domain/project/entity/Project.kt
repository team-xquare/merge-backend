package com.example.mergebackend.domain.project.entity

import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.domain.user.entity.User
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "project")
@DynamicUpdate
class Project(
        id: UUID? = null,
        user: User,
        logo: String,
        projectNameKo: String,
        projectNameEn: String,
        teamNameEn: String,
        description: String,
        githubUrl: String?,
        webUrl: String?,
        playStoreUrl: String?,
        appStoreUrl: String?,
        projectImage: List<String>?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    var user: User = user
        protected set

    @Column(name = "logo", nullable = false)
    var logo: String = logo
        protected set

    @Column(name = "projectNameKo", columnDefinition = "VARCHAR(100)", nullable = false)
    var projectNameKo: String = projectNameKo
        protected set

    @Column(name = "projectNameEn", columnDefinition = "VARCHAR(100)", nullable = false)
    var projectNameEn: String = projectNameEn
        protected set

    @Column(name = "teamNameEn", columnDefinition = "VARCHAR(100)", nullable = false)
    var teamNameEn: String = teamNameEn
        protected set

    @Column(name = "description", columnDefinition = "VARCHAR(500)", nullable = false)
    var description: String = description
        protected set

    @Column(name = "githubUrl", columnDefinition = "VARCHAR(255)")
    var githubUrl: String? = githubUrl
        protected set

    @Column(name = "webUrl", columnDefinition = "VARCHAR(255)")
    var webUrl: String? = webUrl
        protected set

    @Column(name = "playStoreUrl", columnDefinition = "VARCHAR(255)")
    var playStoreUrl: String? = playStoreUrl
        protected set

    @Column(name = "appStoreUrl", columnDefinition = "VARCHAR(255)")
    var appStoreUrl: String? = appStoreUrl
        protected set

    @ElementCollection
    var projectImage: List<String>? = listOf()
        protected set

    fun toResponse() = ProjectDetailResponse(
            this.id!!,
            this.logo,
            this.user.studentName,
            this.projectNameKo,
            this.projectNameEn,
            this.teamNameEn,
            this.description,
            this.githubUrl,
            this.webUrl,
            this.appStoreUrl,
            this.playStoreUrl,
            this.projectImage
    )

    fun toListResponse() = ProjectListResponse(
            this.id!!,
            this.projectNameEn,
            this.teamNameEn,
            this.logo
    )
}