package com.adrian.airquality.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.adrian.airquality.ui.theme.couldbeanicebackground2

@Composable
fun ResultScreenLocation( mainViewModel: MainViewModel, navController: NavController) {

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(
                color = couldbeanicebackground2
            )
    ) {

        item {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(1.dp))
                    RnCard(
                        state = mainViewModel.state,
                        backgroundColor = Color.White,
                        cityName = mainViewModel.city,
                        mainViewModel = mainViewModel,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(1.dp))

                }
                if (mainViewModel.state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(140.dp),
                        color = Color.White
                    )
                }
                mainViewModel.state.error?.let { error ->
                    Text(
                        text = error,
                        color = Color.White,
                        style = TextStyle(fontSize = 20.sp),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 50.dp)
                    )
                }
            }


        }
    }

}
