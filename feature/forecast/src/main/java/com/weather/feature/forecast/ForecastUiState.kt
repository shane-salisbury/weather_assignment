package com.weather.feature.forecast

import com.weather.core.model.DailyForecast

sealed interface ForecastUiState {
    data object Loading : ForecastUiState

    data class Success(val forecast: List<DailyForecast>) : ForecastUiState

    data class Error(val exception: Throwable?) : ForecastUiState
}