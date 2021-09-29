package com.example.newsapp.view_model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    var allNews: MutableState<MutableList<Articles>> = mutableStateOf(mutableListOf())

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
            val response = repository.getAllRemoteNews()
            if (response.isSuccessful) {
                response.body()?.let {
                    allNews.value = it.articles.toMutableList()
                }
                insertNewsToDB()
            }
            Log.d("lolboy", "getAllRemoteNews: remoteList size = ${allNews.value.size}")
        }
    }

    private fun insertNewsToDB() {
        viewModelScope.launch {
            if (repository.getAllLocalNews().isNotEmpty()) {
                var id = 1
                allNews.value.forEach {
                    it.id = id
                    repository.updateNewsIntoDB(it)
                    ++id
                }
            } else {
                var id = 1
                allNews.value.forEach {
                    it.id = id
                    repository.insertNewsIntoDB(it)
                    ++id
                }
            }

            Log.d("lolboy", "insertNewsToDB: localList size = ${repository.getAllLocalNews().size}")
        }
    }

    private fun getLocalNews() {
        viewModelScope.launch {
            allNews.value = repository.getAllLocalNews().toMutableList()
            Log.d("lolboy", "getLocalNews:  localList size = ${allNews.value.size}")
        }
    }

}