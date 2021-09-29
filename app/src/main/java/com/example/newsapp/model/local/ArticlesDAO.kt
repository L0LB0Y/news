package com.example.newsapp.model.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.Articles

@Dao
interface ArticlesDAO {

    @Insert
    suspend fun insert(articles: Articles)

    @Update
    suspend fun update(articles: Articles)

    @Query("select * from articles_table")
    suspend fun getAllLocalNews(): List<Articles>
}