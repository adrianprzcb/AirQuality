package com.adrian.airquality.domain

import java.time.LocalDateTime

data class AirQualityData(
    val time: LocalDateTime,
    val pm10: Double,              // Updated to Double
    val pm25: Double,              // Updated to Double
    val carbonMonoxide: Double,    // Updated to Double
    val nitrogenDioxide: Double,   // Updated to Double
    val sulphurDioxide: Double,    // Updated to Double
    val ozone: Double,             // Updated to Double
    val aerosolOpticalDepth: Double,
    val dust: Double,              // Updated to Double
    val uvIndex: Double,
    val uvIndexClearSky: Double
)
