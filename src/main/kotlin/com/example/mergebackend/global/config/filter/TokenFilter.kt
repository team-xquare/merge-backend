package com.example.mergebackend.global.config.filter

import com.example.mergebackend.global.config.jwt.JwtTokenResolver
import com.example.mergebackend.global.config.jwt.TokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenFilter(
        private val tokenResolver: JwtTokenResolver,
        private val tokenProvider: TokenProvider
): OncePerRequestFilter() {

    @Throws(Exception::class)
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        tokenResolver.resolveToken(request)
                ?.let {
                    SecurityContextHolder.getContext().authentication = tokenProvider.getAuthentication(it)
                }
        filterChain.doFilter(request, response)
    }
}