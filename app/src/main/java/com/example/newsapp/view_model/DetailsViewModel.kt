package com.example.newsapp.view_model

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.newsapp.model.Articles
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(homeViewModel: HomeViewModel): ViewModel() {

     private lateinit var model: MutableState<Articles>
     fun triggerDetailsViewModel(item: Articles): MutableState<Articles> {
          model.value = item
          return model
     }
     
}