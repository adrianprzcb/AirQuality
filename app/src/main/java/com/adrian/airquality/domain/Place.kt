package com.adrian.airquality.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable



data class Place(
    var name: String = "",
    var lat: Double = 0.0,
    var long: Double = 0.0
)