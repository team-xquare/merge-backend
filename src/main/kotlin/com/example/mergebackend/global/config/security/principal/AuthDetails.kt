package com.example.mergebackend.global.config.security.principal

import com.example.mergebackend.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class AuthDetails(
        private val user: User
): UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = ArrayList()

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.accountId

    override fun isAccountNonExpired(): Boolean = false

    override fun isAccountNonLocked(): Boolean = false

    override fun isCredentialsNonExpired(): Boolean = false

    override fun isEnabled(): Boolean = false
}