package com.example.newsapp.view_model

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.local.ArticlesEntity
import com.example.newsapp.model.remote.Articles
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

    private var _allNews = MutableLiveData<List<Articles>>(null)
    val allNews: LiveData<List<Articles>> = _allNews


    init {
        getAllNew()
    }

    private fun getAllNew() {
        if (Constants.isOnline(context)) {
            Constants.createToast("Online Mode !!", context)
            getRemoteNews()
        } else {
            Constants.createToast("Offline Mode !!", context)
            getLocalNews()
        }
    }

    private fun getRemoteNews() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getAllRemoteNews()
            }.onSuccess {
                val data = it.articles
                _allNews.value = data
                insertNewsToDB()
            }.onFailure {
                _allNews.value = null
            }
        }
    }

    private fun insertNewsToDB() {
        viewModelScope.launch {
            var id = 1
            allNews.value?.forEach {
                repository.insertNewsIntoDB(
                    ArticlesEntity(
                        id,
                        it.source,
                        it.author,
                        it.title,
                        it.description,
                        it.url,
                        it.urlToImage,
                        it.publishedAt,
                        it.content
                    )
                )
                ++id
            }
        }
    }

    private fun getLocalNews() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getAllLocalNews()
            }.onSuccess {
                _allNews.value = null
                val list = mutableListOf<Articles>()
                it.forEach { item ->
                    list.add(
                        Articles(
                            item.source,
                            item.author,
                            item.title,
                            item.description,
                            item.url,
                            item.urlToImage,
                            item.publishedAt,
                            item.content
                        )
                    )
                }
                _allNews.value = list.toList()
            }.onFailure {
                _allNews.value = null
            }
        }
    }

}