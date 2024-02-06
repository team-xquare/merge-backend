package com.example.mergebackend.global.config.vault

import com.bettercloud.vault.SslConfig
import com.bettercloud.vault.Vault
import com.bettercloud.vault.VaultConfig
import com.example.mergebackend.global.env.vault.VaultProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class VaultConfig(
    private val vaultProperty: VaultProperty
) {
    @Bean
    fun vault() = Vault(
        VaultConfig()
            .address(vaultProperty.address)
            .token(vaultProperty.vaultToken)
            .sslConfig(SslConfig().verify(false).build())
            .build()
    )
}