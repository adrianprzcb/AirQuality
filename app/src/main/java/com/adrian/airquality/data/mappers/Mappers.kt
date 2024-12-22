package com.adrian.airquality.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.adrian.airquality.data.remote.AirQualityDataDto
import com.adrian.airquality.data.remote.AirQualityDto
import com.adrian.airquality.domain.AirQualityData
import com.adrian.airquality.domain.AirQualityInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: AirQualityData
)


fun AirQualityDataDto.toAirQualityDataMap(): Map<Int, List<AirQualityData>> {
    return time.mapIndexed { index, time ->
        val pm10 = pm10s.getOrNull(index) ?: 0.0
        val pm25 = pm2_5s.getOrNull(index) ?: 0.0
        val carbonMonoxide = carbon_monoxides.getOrNull(index) ?: 0.0
        val nitrogenDioxide = nitrogen_dioxides.getOrNull(index) ?: 0.0
        val sulphurDioxide = sulphur_dioxides.getOrNull(index) ?: 0.0
        val ozone = ozones.getOrNull(index) ?: 0.0
        val aerosolOpticalDepth = aerosol_optical_depths.getOrNull(index) ?: 0.0
        val dust = dusts.getOrNull(index) ?: 0.0
        val uvIndex = uv_indexes.getOrNull(index) ?: 0.0
        val uvIndexClearSky = uv_indexes_clear_sky.getOrNull(index) ?: 0.0

        IndexedWeatherData(
            index = index,
            data = AirQualityData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                pm10 = pm10,
                pm25 = pm25,
                carbonMonoxide = carbonMonoxide,
                nitrogenDioxide = nitrogenDioxide,
                sulphurDioxide = sulphurDioxide,
                ozone = ozone,
                aerosolOpticalDepth = aerosolOpticalDepth,
                dust = dust,
                uvIndex = uvIndex,
                uvIndexClearSky = uvIndexClearSky
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}



fun AirQualityDto.toWeatherInfo(): AirQualityInfo {
    val airQualityDataMap = airQualityData.toAirQualityDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = airQualityDataMap[0]?.find {
        val hour = if(now.minute < 30 || now.hour == 23) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return AirQualityInfo(
        DataPerDay = airQualityDataMap,
        currentData = currentWeatherData
    )
}
