package com.weather.feature.forecast

import com.google.common.truth.Truth.assertThat
import com.weather.core.common.exception.LocationNotProvidedException
import com.weather.core.data.WeatherRepository
import com.weather.core.location.LocationProvider
import com.weather.core.model.LocationCoordinates
import com.weather.core.testing.data.coordinatesTestData
import com.weather.core.testing.data.forecastListTestData
import com.weather.core.testing.location.TestLocationProvider
import com.weather.core.testing.rule.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ForecastViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val weatherRepository: WeatherRepository = mockk(relaxed = true)

    private fun createViewModel(coordinates: LocationCoordinates? = null): ForecastViewModel {
        val locationProvider: LocationProvider = TestLocationProvider(coordinates)
        return ForecastViewModel(weatherRepository, locationProvider)
    }

    @Test
    fun uiState_shouldInitiallyBeLoading() {
        val viewModel = createViewModel()
        assertThat(viewModel.uiState.value).isEqualTo(ForecastUiState.Loading)
    }

    @Test
    fun getForecast_shouldUpdateUiStateToSuccess_whenRepositoryReturnsSuccess() = runTest {
        // Given
        val viewModel = createViewModel(coordinatesTestData)
        val expectedResult = forecastListTestData

        coEvery {
            weatherRepository.getForecast(any())
        } returns Result.success(expectedResult)

        // When
        viewModel.getForecast()

        // Then
        assertThat(viewModel.uiState.value).isEqualTo(ForecastUiState.Success(expectedResult))
        coVerify { weatherRepository.getForecast(any()) }
    }

    @Test
    fun getForecast_shouldUpdateUiStateToError_whenRepositoryReturnsError() = runTest {
        // Given
        val viewModel = createViewModel(coordinatesTestData)
        val exception = Exception("Connection failed")

        coEvery { weatherRepository.getForecast(any()) } returns Result.failure(exception)

        // When
        viewModel.getForecast()

        // Then
        assertThat(viewModel.uiState.value).isEqualTo(ForecastUiState.Error(exception))
        coVerify { weatherRepository.getForecast(any()) }
    }

    @Test
    fun getForecast_shouldUpdateUiStateToError_whenLocationNotProvided() = runTest {
        // Given
        val viewModel = createViewModel(coordinates = null)

        // When
        viewModel.getForecast()

        // Then
        assertThat(viewModel.uiState.value).isInstanceOf(ForecastUiState.Error::class.java)
        assertThat((viewModel.uiState.value as ForecastUiState.Error).exception)
            .isInstanceOf(LocationNotProvidedException::class.java)
    }
}