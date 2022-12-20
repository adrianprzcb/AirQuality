package com.adrian.airquality.ui.presentation

import com.adrian.airquality.domain.AirQualityInfo

data class AirQualityState(
    val weatherInfo: AirQualityInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
