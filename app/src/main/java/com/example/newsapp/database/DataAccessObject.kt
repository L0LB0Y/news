package com.example.newsapp.database

import androidx.room.*
import com.example.newsapp.model.Articles

@Dao
interface DataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: Articles)

    @Query("select * from articles_table")
    suspend fun getAllLocalArticles(): List<Articles>
}