package com.weather.core.testing.data

import com.weather.core.model.HourlyForecast
import com.weather.core.model.Weather
import com.weather.core.network.model.NetworkMain
import com.weather.core.network.model.NetworkWeather
import com.weather.core.network.model.NetworkWeatherDescription
import com.weather.core.network.model.NetworkWind

val weatherTestData = Weather(
    city = "Los Angeles",
    feelsLike = "74°",
    highTemperature = "76°",
    lowTemperature = "71°",
    windSpeed = "1.99 mph",
    windDirection = "SW",
    hourlyForecastList = listOf(
        HourlyForecast(
            time = "3:00 PM",
            temperature = "73° F",
            weatherIconUrl = "https://openweathermap.org/img/wn/ic1@2x.png"
        ),
        HourlyForecast(
            time = "6:00 PM",
            temperature = "77° F",
            weatherIconUrl = "https://openweathermap.org/img/wn/ic2@2x.png"
        ),
    )
)

val networkWeatherTestData = NetworkWeather(
    name = "Los Angeles",
    main = NetworkMain(temp = 72.9, feelsLike = 74.3, tempMin = 71.4, tempMax = 76.1),
    wind = NetworkWind(speed = 1.99, deg = 225),
    weather = listOf(NetworkWeatherDescription("cloudy", "ic0"))
)