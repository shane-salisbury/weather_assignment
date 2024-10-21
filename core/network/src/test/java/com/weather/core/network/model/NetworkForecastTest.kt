package com.weather.core.network.model

import com.google.common.truth.Truth.assertThat
import com.weather.core.testing.data.dailyForecastTestData
import com.weather.core.testing.data.hourlyForecastTestData
import com.weather.core.testing.data.networkForecastTestData
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.datetime.TimeZone
import org.junit.Test

class NetworkForecastTest {

    @Test
    fun mapToTodayHourlyForecastList_shouldCreateHourlyForecast() {
        // Given
        mockkObject(TimeZone)
        val networkForecast = networkForecastTestData
        val expectedResult = hourlyForecastTestData

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val hourlyForecast = networkForecast.mapToTodayHourlyForecastList()

        // Then
        assertThat(hourlyForecast).isEqualTo(expectedResult)
    }

    @Test
    fun mapToDailyForecastList_shouldCreateDailyForecast() {
        // Given
        mockkObject(TimeZone)
        val networkForecast = networkForecastTestData
        val expectedResult = dailyForecastTestData

        every { TimeZone.currentSystemDefault() } returns TimeZone.UTC

        // When
        val dailyForecast = networkForecast.mapToDailyForecastList()

        // Then
        assertThat(dailyForecast).isEqualTo(expectedResult)
    }
}