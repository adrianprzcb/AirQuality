package com.adrian.airquality.ui.presentation

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.location.Address
import android.location.Geocoder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrian.airquality.domain.AdminSQLiteOpenHelper
import com.adrian.airquality.domain.Place
import com.adrian.airquality.domain.location.LocationTracker
import com.adrian.airquality.domain.repository.AirQualityRepository
import com.adrian.airquality.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AirQualityRepository,
    private val locationTracker: LocationTracker,
    application: Application
): AndroidViewModel(application) {


    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    var state by mutableStateOf(AirQualityState())
        private set

    var city : String = ""

    var lat : Double = 0.0
    var long : Double = 0.0



    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val geocoder = Geocoder(getApplication(), Locale.getDefault())
                var addresses: List<Address>? = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                var cityName= addresses!![0].getAddressLine(0)
                city = addresses!![0].locality
                city =  city.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

                var adressess2 = geocoder.getFromLocationName(city , 2)
                lat = location.latitude
                long = location.longitude
                when (val result =
                    repository.getAirQualityData(location.latitude, location.longitude)) {

                    is Resource.Success -> {
                        state = state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location your location. Make sure to grant permissions and enable GPS. \n" +
                            "Sorry for the inconvenience."
                )
            }
        }
    }



    fun loadWeatherForCity(city: String , lat : Double , lon : Double) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            var cityv = city.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            this@MainViewModel.city = cityv

            this@MainViewModel.lat = lat
            long = lon
            when (val result =
                repository.getAirQualityData(lat , lon)) {

                is Resource.Success -> {
                    state = state.copy(
                        weatherInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }



    var places : List<Place> = listOf()


    private fun readCourses() {
        val admin = AdminSQLiteOpenHelper(getApplication(), "clasificacion", null, 1)
        val clasificacion: SQLiteDatabase = admin.getWritableDatabase()
        val cursorCourses =
            clasificacion.rawQuery("SELECT * FROM " + "clasificacion ORDER BY name DESC", null)
        if (cursorCourses.moveToFirst()) {
            do {
                val user = Place(
                    cursorCourses.getString(0),
                    cursorCourses.getDouble(1),
                    cursorCourses.getDouble(2),
                )

                places += user
            } while (cursorCourses.moveToNext())
        }
        cursorCourses.close()
        // multi.append("CLASIFICACIÓN LOCAL \n");

    }

    fun getListPlaces() : List<Place> {
        readCourses()
        return places
    }


   fun saveLocation(city : String , lat: Double , long : Double) {
       val admin = AdminSQLiteOpenHelper(getApplication(), "clasificacion", null, 1)
       val clasificacion: SQLiteDatabase = admin.getWritableDatabase()
       clasificacion.execSQL("INSERT INTO clasificacion (name, lat, long) VALUES ('$city', '$lat', '$long')")
       clasificacion.close()
   }

   }



