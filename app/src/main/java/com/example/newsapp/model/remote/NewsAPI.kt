package com.example.newsapp.model.remote

import com.example.newsapp.model.APIData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/v2/top-headlines")
    suspend fun getAllData(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String
    ): Response<APIData>
}