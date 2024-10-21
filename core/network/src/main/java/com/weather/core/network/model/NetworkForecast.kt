package com.weather.core.network.model

import androidx.annotation.Keep
import com.weather.core.common.degreeToDirection
import com.weather.core.common.epochSecondToInstant
import com.weather.core.common.getEndOfTodayInstant
import com.weather.core.common.iconNameToUrl
import com.weather.core.common.toDateForDailyForecast
import com.weather.core.common.withDegreeAndFahrenheitSymbol
import com.weather.core.common.withMphPostfix
import com.weather.core.model.DailyForecast
import com.weather.core.model.HourlyForecast
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NetworkForecast(
    @SerialName("list")
    val list: List<NetworkForecastItem>
)

fun NetworkForecast.mapToTodayHourlyForecastList(): List<HourlyForecast> {
    return list
        .filter { it.dt.epochSecondToInstant() <= getEndOfTodayInstant() }
        .map { it.mapToHourlyForecast() }
}

fun NetworkForecast.mapToDailyForecastList(): List<DailyForecast> {
    return list
        .map { it.mapToHourlyForecastForDaily() }
        .groupBy { it.localDate }
        .map { (localDate, items) ->
            val highTemperature = items.maxOf { it.temperature }
            val lowTemperature = items.minOf { it.temperature }

            val weatherDescription = items
                .groupingBy { it.weatherDescription }
                .eachCount()
                .maxBy { it.value }
                .key

            val weatherIconUrl = items
                .groupingBy { it.weatherIconName }
                .eachCount()
                .maxBy { it.value }
                .key

            val itemWithMaxWindSpeed = items.maxBy { it.windSpeed }

            DailyForecast(
                date = localDate.toDateForDailyForecast(),
                highTemperature = highTemperature.withDegreeAndFahrenheitSymbol(),
                lowTemperature = lowTemperature.withDegreeAndFahrenheitSymbol(),
                weatherDescription = weatherDescription,
                weatherIconUrl = weatherIconUrl.iconNameToUrl(),
                windSpeed = itemWithMaxWindSpeed.windSpeed.withMphPostfix(),
                windDirection = itemWithMaxWindSpeed.windDirection.degreeToDirection()
            )
        }
}
