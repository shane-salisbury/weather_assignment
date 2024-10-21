package com.weather.core.model

data class DailyForecast(
    val date: String,
    val highTemperature: String,
    val lowTemperature: String,
    val weatherDescription: String,
    val weatherIconUrl: String,
    val windSpeed: String,
    val windDirection: String
)