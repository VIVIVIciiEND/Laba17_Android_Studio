package com.zhanatpaeva.weatherdashboard02.data

import android.health.connect.datatypes.units.Temperature

data class WeatherData(
    val temperature: Int? = null ,
    val humidity: Int? = null,
    val windSpeed: Int? = null ,
    val isLoading: Boolean = false ,
    val error: String? = null
)