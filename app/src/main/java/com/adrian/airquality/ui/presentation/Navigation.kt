package com.adrian.airquality.ui.presentation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adrian.airquality.ui.theme.Teal200
import com.adrian.airquality.ui.theme.fair
import kotlinx.coroutines.delay

@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
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


@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.navigate("main_screen")
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Teal200)
    ) {
        LottieAsset(asset = "lottie.json", restartOnPlay = true)

    }
}