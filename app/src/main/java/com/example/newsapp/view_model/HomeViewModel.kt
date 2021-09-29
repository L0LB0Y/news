package com.example.newsapp.view_model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
//            getLocalNews()
        }
    }

    private fun getRemoteNews() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getAllRemoteNews()
            }.onSuccess {
                val data = it.articles
                _allNews.value = data
                Log.d("lol", "list size = :${data?.size} ")
//                insertNewsToDB()
            }.onFailure {
                _allNews.value = null
                Log.d("lol", "getRemoteNews:${it.message} ")
            }
        }
    }
//
//    private fun insertNewsToDB() {
//        viewModelScope.launch {
//            if (repository.getAllLocalNews().isNotEmpty()) {
//                var id = 1
//                _allNews.value?.forEach {
//                    it.id = id
//                    repository.updateNewsIntoDB(it)
//                    ++id
//                }
//            } else {
//                var id = 1
//                _allNews.value?.forEach {
//                    it.id = id
//                    repository.insertNewsIntoDB(it)
//                    ++id
//                }
//            }
//        }
//    }
//
//    private fun getLocalNews() {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                repository.getAllLocalNews()
//            }.onSuccess {
//                _allNews.value = it
//            }.onFailure {
//                _allNews.value = null
//                Log.d("lol", "getRemoteNews:${it.message} ")
//            }
//        }
//    }

}