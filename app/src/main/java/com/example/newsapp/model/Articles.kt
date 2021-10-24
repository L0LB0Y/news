package com.example.newsapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@kotlinx.parcelize.Parcelize
@Serializable
@Entity(tableName = "articles_table")
data class Articles(
    @PrimaryKey var id: Int? = null,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
): Parcelable