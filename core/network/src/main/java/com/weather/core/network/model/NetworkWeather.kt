package com.weather.core.network.model

import androidx.annotation.Keep
import com.weather.core.common.degreeToDirection
import com.weather.core.common.withDegreeSymbol
import com.weather.core.common.withMphPostfix
import com.weather.core.model.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NetworkWeather(
    @SerialName("weather")
    val weather: List<NetworkWeatherDescription>,
    @SerialName("main")
    val main: NetworkMain,
    @SerialName("wind")
    val wind: NetworkWind,
    @SerialName("name")
    val name: String,
)

fun NetworkWeather.mapToWeatherWith(networkForecast: NetworkForecast): Weather {
    return Weather(
        city = name,
        feelsLike = main.feelsLike.withDegreeSymbol(),
        highTemperature = main.tempMax.withDegreeSymbol(),
        lowTemperature = main.tempMin.withDegreeSymbol(),
        windSpeed = wind.speed.withMphPostfix(),
        windDirection = wind.deg.degreeToDirection(),
        hourlyForecastList = networkForecast.mapToTodayHourlyForecastList()
    )
}