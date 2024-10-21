package com.weather.core.model

import kotlinx.datetime.LocalDate

data class HourlyForecastForDaily(
    val localDate: LocalDate,
    val temperature: Double,
    val weatherDescription: String,
    val weatherIconName: String,
    val windSpeed: Double,
    val windDirection: Int
)