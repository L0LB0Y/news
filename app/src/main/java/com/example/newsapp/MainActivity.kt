package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.newsapp.view.Details
import com.example.newsapp.view.Home
import com.example.newsapp.view_model.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navHostController = rememberNavController()

            NavHost(
                navController = navHostController,
                startDestination = "Home",
                modifier = Modifier.background(Color(0xff0a0959))
            ) {
                composable("Home") {
                    Home(navHostController, homeViewModel)
                }
                composable(
                    "Details/{index}", arguments = listOf(
                        navArgument("index", builder = { type = NavType.IntType })
                    )
                ) { navBackStack ->

                    val index = navBackStack.arguments?.getInt("index")
                    Details(index!!, navHostController = navHostController, homeViewModel)
                }

            }
        }
    }
}