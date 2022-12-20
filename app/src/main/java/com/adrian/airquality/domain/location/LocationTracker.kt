package com.adrian.airquality.domain.location

import android.location.Address
import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}