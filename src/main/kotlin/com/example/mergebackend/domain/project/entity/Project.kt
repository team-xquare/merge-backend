package com.example.mergebackend.domain.project.entity

import com.example.mergebackend.domain.project.presentation.dto.response.ProjectDetailResponse
import com.example.mergebackend.domain.project.presentation.dto.response.ProjectListResponse
import com.example.mergebackend.domain.user.entity.User
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDate
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
    projectImage: List<String>?,
    date: LocalDate
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
    var projectImage: List<String>? = projectImage
        protected set

    @Column(name = "isHidden", columnDefinition = "BIT", nullable = false)
    var isHidden: Boolean = false

    @Column(name = "date", columnDefinition = "DATE", nullable = false)
    var date: LocalDate = date

    fun toResponse(user: User?) = ProjectDetailResponse(
        id = this.id!!,
        logo = this.logo,
        studentName = this.user.studentName,
        projectNameKo = this.projectNameKo,
        projectNameEn = this.projectNameEn,
        teamNameEn = this.teamNameEn,
        description = this.description,
        githubUrl = this.githubUrl,
        webUrl = this.webUrl,
        appStoreUrl = this.appStoreUrl,
        playStoreUrl = this.playStoreUrl,
        projectImage = this.projectImage,
        isManagedByMe = (user == this.user),
        date = this.date,
        isHidden = this.isHidden
    )

    fun toListResponse() = ProjectListResponse(
        this.id!!,
        this.projectNameKo,
        this.teamNameEn,
        this.logo,
        this.date,
        isHidden = this.isHidden
    )
}