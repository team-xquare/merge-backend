package com.example.mergebackend.infra.vault

import com.bettercloud.vault.Vault
import com.example.mergebackend.infra.vault.exception.VaultException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException.InternalServerError
import java.lang.Exception

@Service
private class VaultUtilImpl(
    private val vault: Vault
): VaultUtil {

    companion object {
        const val XQUARE_PATH = "xquare-kv"
    }
    override fun addSecret(secrets: Map<String, String>, path: String) {
        val response = vault.logical().write("${XQUARE_PATH}/$path", secrets)
        if(response.restResponse.status != 200) {
            println(response.restResponse.body)
            throw VaultException
        }
    }
}