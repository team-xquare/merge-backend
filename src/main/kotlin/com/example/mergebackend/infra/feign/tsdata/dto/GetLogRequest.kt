package com.example.mergebackend.infra.feign.tsdata.dto

data class GetLogRequest(
    val queries: List<QueryDto>,
    val from: String,
    val to: String
)