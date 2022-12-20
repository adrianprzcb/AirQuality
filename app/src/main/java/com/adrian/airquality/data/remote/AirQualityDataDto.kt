package com.adrian.airquality.data.remote

import com.squareup.moshi.Json

data class AirQualityDataDto(
    val time : List<String>,
    @field:Json(name = "pm10")
    val pm10s : List<Int>,
    @field:Json(name = "pm2_5")
    val pm2_5s : List<Int>,
    @field:Json(name = "carbon_monoxide")
    val carbon_monoxides : List<Int>,
    @field:Json(name = "nitrogen_dioxide")
    val nitrogen_dioxides : List<Int>,
    @field:Json(name = "sulphur_dioxide")
    val sulphur_dioxides : List<Int>,
    @field:Json(name = "ozone")
    val ozones : List<Int>,
    @field:Json(name = "aerosol_optical_depth")
    val aerosol_optical_depths : List<Double>,
    @field:Json(name = "dust")
    val dusts : List<Int>,
    @field:Json(name = "uv_index")
    val uv_indexes : List<Double>,
    @field:Json(name = "uv_index_clear_sky")
    val uv_indexes_clear_sky: List<Double>
)
