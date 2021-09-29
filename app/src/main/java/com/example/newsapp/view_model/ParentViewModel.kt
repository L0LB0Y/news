package com.example.newsapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.remote.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor() : ViewModel() {
    lateinit var articles: LiveData<Articles>
    fun getArticlesObject(item: Articles) {
        articles = MutableLiveData(item)
    }
}