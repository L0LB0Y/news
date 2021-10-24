package com.example.newsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.model.Articles

@Database(entities = [Articles::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun getDao(): DataAccessObject
}