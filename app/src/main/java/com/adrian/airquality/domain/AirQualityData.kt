package com.adrian.airquality.domain

import java.time.LocalDateTime

data class AirQualityData(
    val time: LocalDateTime,
    val pm10: Int,
    val pm25: Int,
    val carbonMonoxide: Int,
    val nitrogenDioxide: Int,
    val sulphurDioxide: Int,
    val ozone: Int,
    val aerosolOpticalDepth: Double,
    val dust: Int,
    val uvIndex: Double,
    val uvIndexClearSky: Double
)
