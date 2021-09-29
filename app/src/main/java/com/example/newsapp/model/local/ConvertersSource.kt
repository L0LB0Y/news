package com.example.newsapp.model.local

import androidx.room.TypeConverter
import com.example.newsapp.model.remote.Source

class ConvertersSource {

    @TypeConverter
    fun fromSource(source: Source?): String? {
        return source?.name
    }

    @TypeConverter
    fun toSource(name: String?): Source? {
        return if (name == null)
            null
        else
            Source(name, name)
    }
}