package com.example.newsapp.view_model

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Articles
import com.example.newsapp.other.Constants
import com.example.newsapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val repository: Repository,
    private val context: Context
) :
    ViewModel() {

    private var _allNews = MutableLiveData<List<Articles>>(listOf())
    val allNews: LiveData<List<Articles>> = _allNews


    init {
        getAllNew()
    }

    private fun getAllNew() {
        if (Constants.isOnline(context)) {
            Constants.createToast("Online Mode !!", context)
            getRemoteArticles()
        } else {
            Constants.createToast("Offline Mode !!", context)
            getLocalArticles()
        }
    }

    private fun getRemoteArticles() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getAllRemoteNews()
            }.onSuccess {
                val data = it.articles
                _allNews.value = data
                insertArticlesIntoDB(it.articles)
            }.onFailure {
                _allNews.value = null
            }
        }
    }

    private fun insertArticlesIntoDB(articles: List<Articles>) {
        viewModelScope.launch {
            var id = 1
            articles.forEach {
                it.id = id
                repository.insertArticleIntoDB(it)
                ++id
            }
        }
    }

    private fun getLocalArticles() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getAllLocalArticles()
            }.onSuccess {
                val data = it
                _allNews.value = data
            }.onFailure {
                _allNews.value = null
            }
        }
    }

}