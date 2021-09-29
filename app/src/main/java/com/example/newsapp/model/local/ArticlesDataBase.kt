package com.example.newsapp.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.model.Articles

@Database(entities = [Articles::class], version = 1)
@TypeConverters(ConvertersSource::class)
abstract class ArticlesDataBase : RoomDatabase() {

    abstract fun getDao(): ArticlesDAO

}