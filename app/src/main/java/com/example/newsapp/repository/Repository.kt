package com.example.newsapp.repository

import com.example.newsapp.model.remote.APIData
import com.example.newsapp.model.remote.Articles
import com.example.newsapp.model.local.ArticlesDAO
import com.example.newsapp.model.local.ArticlesEntity
import com.example.newsapp.other.Constants
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class Repository @Inject constructor(private val client: HttpClient, private val dao: ArticlesDAO) {


    suspend fun insertNewsIntoDB(articles: ArticlesEntity) {
        dao.insert(articles)
    }

    suspend fun updateNewsIntoDB(articles: ArticlesEntity) {
        dao.update(articles)
    }

    suspend fun getAllLocalNews(): List<ArticlesEntity> {
        return dao.getAllLocalNews()
    }


    suspend fun getAllRemoteNews(): APIData {
        return client.get(urlString = Constants.URL) as APIData
    }
}