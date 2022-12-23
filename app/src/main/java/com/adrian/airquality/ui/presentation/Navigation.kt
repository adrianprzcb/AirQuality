package com.adrian.airquality.ui.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
      /*  composable("splash_screen") {
            SplashScreen(navController = navController)
        }*/
        composable("main_screen") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MainSearchScreen( navController, viewModel)
            }
        }
        composable("result_screen_no") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ResultScreen( viewModel, navController)
            }
        }
        composable("result_screen_yes") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ResultScreenLocation( viewModel , navController)
            }
        }
    }
}