package com.weather.feature.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.core.common.exception.LocationNotProvidedException
import com.weather.core.data.WeatherRepository
import com.weather.core.location.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationProvider: LocationProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<ForecastUiState>(ForecastUiState.Loading)
    val uiState: StateFlow<ForecastUiState> = _uiState.asStateFlow()

    /**
     * Gets 5 day forecast information from the repository and updates the UI state accordingly.
     */
    fun getForecast() {
        locationProvider.getLocationCoordinates { coordinates ->
            if (coordinates == null) {
                _uiState.value = ForecastUiState.Error(LocationNotProvidedException())
                return@getLocationCoordinates
            }

            viewModelScope.launch {
                _uiState.value = ForecastUiState.Loading

                weatherRepository.getForecast(coordinates)
                    .onSuccess { _uiState.value = ForecastUiState.Success(it) }
                    .onFailure { _uiState.value = ForecastUiState.Error(it) }
            }
        }
    }
}