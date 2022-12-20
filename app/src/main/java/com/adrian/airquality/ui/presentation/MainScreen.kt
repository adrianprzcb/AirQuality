package com.adrian.airquality.ui.presentation

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.location.Geocoder
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adrian.airquality.domain.AdminSQLiteOpenHelper
import com.adrian.airquality.domain.Place
import com.adrian.airquality.ui.theme.couldbeanicebackground
import com.adrian.airquality.ui.theme.couldbeanicebackground2
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun MainSearchScreen(navController: NavController, mainViewModel: MainViewModel) {

        val searchWidgetState by mainViewModel.searchWidgetState
        val searchTextState by mainViewModel.searchTextState
        val context = LocalContext.current

    var savedPlacesList: List<Place> = mainViewModel.getListPlaces()

        Scaffold(
            topBar = {
                MainAppBar(
                    searchWidgetState = searchWidgetState,
                    searchTextState = searchTextState,
                    onTextChange = {
                        mainViewModel.updateSearchTextState(newValue = it)
                    },
                    onCloseClicked = {
                        mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    },
                    onSearchClicked = {
                        Log.d("Searched Text", it)
                        searchCity(navController, mainViewModel , it)
                    },
                    onSearchTriggered = {
                        mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                    },

                )
            },

        ){}
    Box(modifier = Modifier
        .padding(top = 53.dp)
        .background(couldbeanicebackground)
        .fillMaxSize()) {


        LazyColumn(modifier = Modifier.padding(top = 40.dp)) {
            var placess: List<Place> = listOf()
            for (place in savedPlacesList) {

                if (!placess.contains(place)) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable {
                                    mainViewModel.updateSearchTextState(newValue = place.name)
                                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                                    searchCity(navController, mainViewModel, place.name)
                                },
                            elevation = 10.dp,
                            backgroundColor = Color.White
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(text = place.name)
                            }
                        }
                    }
                    placess += place
                }
            }
               /* items(savedPlacesList.size) { index ->
                    val place = savedPlacesList[index]
                    SavedPlaceItem(place = place, onClick = {
                        mainViewModel.updateSearchTextState(newValue = place.name)
                        mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                        searchCity(navController, mainViewModel, place.name)
                    })
                }*/
       /*  if (savedPlacesList != null) {
                    items(savedPlacesList!!.size) { index ->
                        Text(text = savedPlacesList!![index].name)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                } else {
                    item {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text(text = "No saved places")
                        }
                    }
            }*/
        }
    }


    }

@Composable
fun SavedPlaceItem(place: Place, onClick: () -> Unit) {
    Text(text = place.name, modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(top = 16.dp))


}

fun searchCity(navController: NavController, mainViewModel: MainViewModel , city: String) {

    val geocoder = Geocoder(mainViewModel.getApplication() , Locale.getDefault())
    var address = geocoder.getFromLocationName(city, 2)

    if (address!!.size > 0) {
        val place = Place( city , address[0].latitude , address[0].longitude )
        mainViewModel.loadWeatherForCity(city , address[0].latitude , address[0].longitude)
        navController.navigate("result_screen_no")
    } else {
        Log.d("Error", "No address found")
    }


}



@Composable
    fun MainAppBar(
        searchWidgetState: SearchWidgetState,
        searchTextState: String,
        onTextChange: (String) -> Unit,
        onCloseClicked: () -> Unit,
        onSearchClicked: (String) -> Unit,
        onSearchTriggered: () -> Unit
    ) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                DefaultAppBar(
                    onSearchClicked = onSearchTriggered
                )
            }
            SearchWidgetState.OPENED -> {
                SearchAppBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked,
                    onSearchClicked = onSearchClicked
                )
            }
        }
    }

    @Composable
    fun DefaultAppBar(onSearchClicked: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = "Search for a place "
                , color = Color.Black
                )
            },
            backgroundColor = couldbeanicebackground2,
            modifier = Modifier.clickable { onSearchClicked() },
            actions = {
                IconButton(
                    onClick = { onSearchClicked() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Black
                    )
                }
            }
        )
    }

    @Composable
    fun SearchAppBar(
        text: String,
        onTextChange: (String) -> Unit,
        onCloseClicked: () -> Unit,
        onSearchClicked: (String) -> Unit,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            elevation = AppBarDefaults.TopAppBarElevation,
            color = couldbeanicebackground
        ) {
            TextField(modifier = Modifier
                .fillMaxWidth(),
                value = text,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = "Search for a place...",
                        color = Color.Black
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Black
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = Color.Black
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
                ))
        }
    }


    @Composable
    @Preview
    fun DefaultAppBarPreview() {
        DefaultAppBar(onSearchClicked = {})
    }

    @Composable
    @Preview
    fun SearchAppBarPreview() {
        SearchAppBar(
            text = "Some random text",
            onTextChange = {},
            onCloseClicked = {},
            onSearchClicked = {}
        )
    }



