package com.example.newsapp.repository

import com.example.newsapp.model.APIData
import com.example.newsapp.model.Articles
import com.example.newsapp.model.local.ArticlesDAO
import com.example.newsapp.model.remote.NewsAPI
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val newsAPI: NewsAPI, private val dao: ArticlesDAO) {


    suspend fun insertNewsIntoDB(articles: Articles) {
        dao.insert(articles)
    }
    suspend fun updateNewsIntoDB(articles: Articles) {
        dao.update(articles)
    }

    suspend fun getAllLocalNews(): List<Articles> {
        return dao.getAllLocalNews()
    }


    suspend fun getAllRemoteNews(): Response<APIData> {
        return newsAPI.getAllData("e74ef58d62ab46328e2f94d8d8c6cad2", "ae")
    }
}