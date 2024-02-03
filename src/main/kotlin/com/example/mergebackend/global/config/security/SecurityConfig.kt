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
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.util.*

@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val tokenProvider: TokenProvider,
        private val exceptionHandlerFilter: ExceptionHandlerFilter,
        private val tokenResolver: JwtTokenResolver
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .csrf().disable()
                .formLogin().disable()
                .cors()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/auth/sms").permitAll()
                .antMatchers(HttpMethod.GET, "/auth/sms/check").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                .and()

                .apply(FilterConfig(tokenProvider, tokenResolver, exceptionHandlerFilter))
                .and().build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun bankEncoder(): Base64.Encoder = Base64.getEncoder()

    @Bean
    fun logger(): KLogger = KotlinLogging.logger {}
}