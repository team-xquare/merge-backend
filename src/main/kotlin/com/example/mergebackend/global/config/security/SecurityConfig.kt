package com.example.mergebackend.global.config.security

import com.example.mergebackend.global.config.error.handler.ExceptionHandlerFilter
import com.example.mergebackend.global.config.filter.FilterConfig
import com.example.mergebackend.global.config.jwt.JwtTokenResolver
import com.example.mergebackend.global.config.jwt.TokenProvider
import mu.KLogger
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils
import java.util.*

@Configuration
class SecurityConfig(
        private val tokenProvider: TokenProvider,
        private val exceptionHandlerFilter: ExceptionHandlerFilter,
        private val tokenResolver: JwtTokenResolver
) {

    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .formLogin().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest).permitAll()
            .antMatchers(HttpMethod.POST, "/auth").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .cors().and()
            .headers().frameOptions().sameOrigin().and()
            .apply(FilterConfig(tokenProvider, tokenResolver, exceptionHandlerFilter))
        return http.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun bankEncoder(): Base64.Encoder = Base64.getEncoder()

    @Bean
    fun logger(): KLogger = KotlinLogging.logger {}
}