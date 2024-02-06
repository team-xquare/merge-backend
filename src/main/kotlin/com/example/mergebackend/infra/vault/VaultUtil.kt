package com.example.mergebackend.infra.vault

interface VaultUtil {
    fun addSecret(secrets: Map<String, String>, path: String)
}