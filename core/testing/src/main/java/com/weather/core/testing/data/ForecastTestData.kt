package com.weather.core.testing.data

import com.weather.core.model.DailyForecast
import com.weather.core.model.HourlyForecast
import com.weather.core.model.HourlyForecastForDaily
import com.weather.core.network.model.NetworkForecast
import com.weather.core.network.model.NetworkForecastItem
import com.weather.core.network.model.NetworkMain
import com.weather.core.network.model.NetworkWeatherDescription
import com.weather.core.network.model.NetworkWind
import kotlinx.datetime.LocalDate

val networkForecastTestData = NetworkForecast(
    list=listOf(
        NetworkForecastItem(
            dt=1729533600,
            main=NetworkMain(temp=65.62, feelsLike=62.55, tempMin=63.73, tempMax=65.62),
            weather= listOf(NetworkWeatherDescription(description="broken clouds", icon="04n")),
            wind=NetworkWind(speed=12.84, deg=284)),
        NetworkForecastItem(
            dt=1729544400,
            main=NetworkMain(temp=62.02, feelsLike=59.0, tempMin=59.74, tempMax=62.02),
            weather=listOf(NetworkWeatherDescription(description="scattered clouds", icon="03n")),
            wind=NetworkWind(speed=11.18, deg=305)),
        NetworkForecastItem(
            dt=1729555200,
            main=NetworkMain(temp=56.73, feelsLike=53.71, tempMin=56.73, tempMax=56.73),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01n")),
            wind=NetworkWind(speed=9.84, deg=323)),
        NetworkForecastItem(
            dt=1729566000,
            main=NetworkMain(temp=54.97, feelsLike=51.62, tempMin=54.97, tempMax=54.97),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            wind=NetworkWind(speed=8.72, deg=333)),
        NetworkForecastItem(
            dt=1729576800,
            main=NetworkMain(temp=59.65, feelsLike=56.39, tempMin=59.65, tempMax=59.65),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            wind=NetworkWind(speed=3.09, deg=253)),
        NetworkForecastItem(
            dt=1729587600,
            main=NetworkMain(temp=62.92, feelsLike=59.99, tempMin=62.92, tempMax=62.92),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            wind=NetworkWind(speed=7.36, deg=209)),
        NetworkForecastItem(
            dt=1729598400,
            main=NetworkMain(temp=63.27, feelsLike=60.28, tempMin=63.27, tempMax=63.27),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01d")),
            wind=NetworkWind(speed=6.91, deg=212)),
        NetworkForecastItem(
            dt=1729609200,
            main=NetworkMain(temp=61.66, feelsLike=58.57, tempMin=61.66, tempMax=61.66),
            weather=listOf(NetworkWeatherDescription(description="clear sky", icon="01n")),
            wind=NetworkWind(speed=3.49, deg=237)),
    )
)

val networkHourlyForecastTestData = NetworkForecast(
    list = listOf(
        NetworkForecastItem(
            dt = 1729522800,
            NetworkMain(temp = 73.3, feelsLike = 74.6, tempMin = 71.4, tempMax = 76.5),
            weather = listOf(NetworkWeatherDescription("cloudy", "ic1")),
            NetworkWind(speed = 1.99, deg = 225)
        ),
        NetworkForecastItem(
            dt = 1729533600,
            NetworkMain(temp = 77.2, feelsLike = 74.6, tempMin = 71.4, tempMax = 76.5),
            weather = listOf(NetworkWeatherDescription("cloudy", "ic2")),
            NetworkWind(speed = 1.99, deg = 225)
        )
    )
)

val forecastListTestData = listOf(
    DailyForecast(
        date = "Monday",
        highTemperature = "76°",
        lowTemperature = "71°",
        weatherDescription = "Partly cloudy",
        weatherIconUrl = "",
        windSpeed = "1.23 mph",
        windDirection = "SW",
    ),
    DailyForecast(
        date = "Tuesday",
        highTemperature = "76°",
        lowTemperature = "71°",
        weatherDescription = "Sunny",
        weatherIconUrl = "",
        windSpeed = "1.23 mph",
        windDirection = "SW",
    ),
    DailyForecast(
        date = "Wednesday",
        highTemperature = "76°",
        lowTemperature = "71°",
        weatherDescription = "Rainy",
        weatherIconUrl = "",
        windSpeed = "1.23 mph",
        windDirection = "SW",
    )
)

val dailyForecastTestData = listOf(
    DailyForecast(
        date="Monday, October 21",
        highTemperature="66° F",
        lowTemperature="62° F",
        weatherDescription="broken clouds",
        weatherIconUrl="https://openweathermap.org/img/wn/04n@2x.png",
        windSpeed="12.84 mph",
        windDirection="W"
    ),
    DailyForecast(
        date="Tuesday, October 22",
        highTemperature="63° F",
        lowTemperature="55° F",
        weatherDescription="clear sky",
        weatherIconUrl="https://openweathermap.org/img/wn/01d@2x.png",
        windSpeed="9.84 mph",
        windDirection="NW"
    )
)

val hourlyForecastTestData = listOf(
    HourlyForecast(
        time="6:00 PM",
        temperature="66° F",
        weatherIconUrl="https://openweathermap.org/img/wn/04n@2x.png"
    ),
    HourlyForecast(
        time="9:00 PM",
        temperature="62° F",
        weatherIconUrl="https://openweathermap.org/img/wn/03n@2x.png"
    )
)

val hourlyForecastForDaily = HourlyForecastForDaily(
    localDate = LocalDate(2024, 10, 21),
    temperature=65.62,
    weatherDescription="broken clouds",
    weatherIconName="04n",
    windSpeed=12.84,
    windDirection=284
)