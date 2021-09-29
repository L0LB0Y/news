package com.example.newsapp.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class APIData(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Articles>?
)