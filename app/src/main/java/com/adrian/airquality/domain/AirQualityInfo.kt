package com.adrian.airquality.domain

data class AirQualityInfo(
    val DataPerDay: Map<Int, List<AirQualityData>>,
    val currentData: AirQualityData?
)
