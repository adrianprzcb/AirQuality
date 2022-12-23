package com.adrian.airquality.ui.presentation

import android.Manifest
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.database.getDoubleOrNull
import androidx.datastore.core.DataStore
import com.adrian.airquality.R
import com.adrian.airquality.domain.AdminSQLiteOpenHelper
import com.adrian.airquality.domain.Place
import com.adrian.airquality.ui.theme.AirQualityTheme
import com.adrian.airquality.ui.theme.couldbeanicebackground
import com.adrian.airquality.ui.theme.couldbeanicebackground2
import com.adrian.airquality.ui.theme.couldbeanicebackground3
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.statusBarColor = couldbeanicebackground3.toArgb()
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
           // mainViewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        setContent {
            AirQualityTheme {
                Navigation(viewModel = mainViewModel)
            }
        }
    }





}


