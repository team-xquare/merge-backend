package com.example.mergebackend.infra.feign.tsdata.dto

data class QueryDto(
    val expr: String,
    val refId: String,
    val datasource: String
)

