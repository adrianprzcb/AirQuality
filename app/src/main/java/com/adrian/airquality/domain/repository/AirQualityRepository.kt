package com.adrian.airquality.domain.repository

import com.adrian.airquality.domain.AirQualityInfo
import com.adrian.airquality.domain.util.Resource


interface AirQualityRepository {
    suspend fun getAirQualityData(lat: Double, long: Double): Resource<AirQualityInfo>
}