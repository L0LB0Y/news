package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.newsapp.model.Articles
import com.example.newsapp.view.Details
import com.example.newsapp.view.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navHostController = rememberNavController()

            NavHost(
                navController = navHostController,
                startDestination = "Home",
                modifier = Modifier.background(Color(0xfff9f2ec))
            ) {
                composable("Home") {
                    Home(navHostController)
                }
                composable("Details") {
                    val articles =
                        navHostController.previousBackStackEntry?.savedStateHandle?.get<Articles>("Articles")
                    articles?.let {
                        Details(navHostController, it)
                    }
                }

            }
        }
    }
}