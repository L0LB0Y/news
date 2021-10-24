package com.example.newsapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val articles: List<Articles>
)