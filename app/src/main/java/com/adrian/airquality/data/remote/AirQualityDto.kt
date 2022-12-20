package com.adrian.airquality.data.remote

import com.squareup.moshi.Json

data class AirQualityDto(
    @field:Json(name = "hourly")
    val airQualityData: AirQualityDataDto
)
