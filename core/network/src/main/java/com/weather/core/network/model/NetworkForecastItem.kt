package com.weather.core.network.model

import androidx.annotation.Keep
import com.weather.core.common.epochSecondToAmPmTime
import com.weather.core.common.epochSecondToLocalDate
import com.weather.core.common.iconNameToUrl
import com.weather.core.common.withDegreeAndFahrenheitSymbol
import com.weather.core.model.HourlyForecast
import com.weather.core.model.HourlyForecastForDaily
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NetworkForecastItem(
    @SerialName("dt")
    val dt: Long,
    @SerialName("main")
    val main: NetworkMain,
    @SerialName("weather")
    val weather: List<NetworkWeatherDescription>,
    @SerialName("wind")
    val wind: NetworkWind,
)

fun NetworkForecastItem.mapToHourlyForecast(): HourlyForecast {
    return HourlyForecast(
        time = dt.epochSecondToAmPmTime(),
        temperature = main.temp.withDegreeAndFahrenheitSymbol(),
        weatherIconUrl = weather.first().icon.iconNameToUrl()
    )
}

fun NetworkForecastItem.mapToHourlyForecastForDaily(): HourlyForecastForDaily {
    return HourlyForecastForDaily(
        localDate = dt.epochSecondToLocalDate(),
        temperature = main.temp,
        weatherDescription = weather.first().description,
        weatherIconName = weather.first().icon,
        windSpeed = wind.speed,
        windDirection = wind.deg
    )
}