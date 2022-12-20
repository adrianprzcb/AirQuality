package com.adrian.airquality.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityApi {

    @GET("v1/air-quality?hourly=pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone,aerosol_optical_depth,dust,uv_index,uv_index_clear_sky")
    suspend fun getAirQualityData(
        @Query("latitude") lat: Double ,
        @Query("longitude") long: Double
    ): AirQualityDto
}