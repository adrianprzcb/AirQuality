package com.adrian.airquality.data.repository

import com.adrian.airquality.data.mappers.toWeatherInfo
import com.adrian.airquality.data.remote.AirQualityApi
import com.adrian.airquality.domain.AirQualityInfo
import com.adrian.airquality.domain.repository.AirQualityRepository
import com.adrian.airquality.domain.util.Resource

import javax.inject.Inject

class AirQualityRepositoryImpl @Inject constructor(
    private val api: AirQualityApi
): AirQualityRepository {

    override suspend fun getAirQualityData(lat: Double, long: Double): Resource<AirQualityInfo> {
        return try {
            Resource.Success(
                data = api.getAirQualityData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}