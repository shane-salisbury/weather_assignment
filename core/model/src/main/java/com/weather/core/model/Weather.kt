package com.weather.core.model

data class Weather(
    val city: String,
    val feelsLike: String,
    val highTemperature: String,
    val lowTemperature: String,
    val windSpeed: String,
    val windDirection: String,
    val hourlyForecastList: List<HourlyForecast>
)