package com.adrian.airquality.domain

import java.time.LocalDateTime

data class AirQualityData(
    val time: LocalDateTime,
    val pm10: Double?,
    val pm25: Double?,
    val carbonMonoxide: Double?,
    val nitrogenDioxide: Double?,
    val sulphurDioxide: Double?,
    val ozone: Double?,
    val aerosolOpticalDepth: Double?,
    val dust: Double?,
    val uvIndex: Double?,
    val uvIndexClearSky: Double?
)
