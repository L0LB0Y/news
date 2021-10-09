package com.example.newsapp.model.local

import androidx.room.*

@Dao
interface ArticlesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: ArticlesEntity)

    @Query("select * from articles_table")
    suspend fun getAllLocalNews(): List<ArticlesEntity>
}