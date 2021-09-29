package com.example.newsapp.model
import com.example.newsapp.model.Articles
import com.google.gson.annotations.SerializedName

data class APIData (

	@SerializedName("status") val status : String,
	@SerializedName("totalResults") val totalResults : Int,
	@SerializedName("articles") val articles : List<Articles>
)