package com.example.newsapp.model.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ArticlesDAO {

    @Insert
    suspend fun insert(articles: ArticlesEntity)

    @Update
    suspend fun update(articles: ArticlesEntity)

    @Query("select * from articles_table")
    suspend fun getAllLocalNews(): List<ArticlesEntity>
}