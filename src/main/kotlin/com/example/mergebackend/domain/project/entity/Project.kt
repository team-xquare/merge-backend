package com.example.mergebackend.domain.project.entity

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
) {

    @Id @Column(name = "id")
    var id: UUID? = id
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id", columnDefinition = "BINARY(16)")
    var user: User = user
        protected set

    @Column(name = "logo", columnDefinition = "VARCHAR(200)", nullable = false)
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

    @Column(name = "githubUrl", columnDefinition = "VARCHAR(100)")
    var githubUrl: String? = githubUrl
        protected set

    @Column(name = "webUrl", columnDefinition = "VARCHAR(100)")
    var webUrl: String? = webUrl
        protected set

    @Column(name = "playStoreUrl", columnDefinition = "VARCHAR(100)")
    var playStoreUrl: String? = playStoreUrl
        protected set

    @Column(name = "appStoreUrl", columnDefinition = "VARCHAR(100)")
    var appStoreUrl: String? = appStoreUrl
        protected set

    @OneToMany(mappedBy = "project", cascade = [CascadeType.ALL], orphanRemoval = true)
    var projectImage: List<ProjectImage> = listOf()
}