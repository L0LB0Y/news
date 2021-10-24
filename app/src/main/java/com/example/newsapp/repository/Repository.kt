package com.example.newsapp.repository

import com.example.newsapp.model.Response
import com.example.newsapp.database.DataAccessObject
import com.example.newsapp.model.Articles
import com.example.newsapp.other.Constants.URL
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class Repository @Inject constructor(private val client: HttpClient, private val dao: DataAccessObject) {


    suspend fun insertArticleIntoDB(articles: Articles) {
        dao.insertArticles(articles)
    }

    suspend fun getAllLocalArticles(): List<Articles> {
        return dao.getAllLocalArticles()
    }

    suspend fun getAllRemoteNews(): Response {
        return client.get(urlString = URL) as Response
    }
}