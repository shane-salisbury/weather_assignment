package com.weather.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.weather.core.designsystem.components.WeatherProgressIndicator
import com.weather.core.designsystem.theme.WeatherTheme
import com.weather.core.model.HourlyForecast
import com.weather.core.model.Weather
import kotlin.math.roundToInt

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            viewModel.getWeather()
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    HomeScreen(
        uiState = uiState,
        getWeather = viewModel::getWeather,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    uiState: HomeUiState,
    getWeather: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(uiState) {
        isRefreshing = false
    }

    Scaffold(
        modifier = modifier.pullToRefresh(
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                getWeather()
            }
        )
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            when (uiState) {
                HomeUiState.Loading -> WeatherProgressIndicator()

                is HomeUiState.Error -> ErrorView(
                    exception = uiState.exception,
                    retry = getWeather
                )

                is HomeUiState.Success -> HomeDetails(
                    weather = uiState.weather,
                    modifier = Modifier,
                )
            }

            if (pullToRefreshState.distanceFraction > 0) {
                WeatherProgressIndicator(
                    progress = { pullToRefreshState.distanceFraction },
                    modifier = Modifier.offset {
                        IntOffset(
                            x = 0,
                            y = pullToRefreshState.distanceFraction.times(100).roundToInt()
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun HomeDetails(
    weather: Weather,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        weatherItems(weather)
    }
}

private fun LazyListScope.weatherItems(
    weather: Weather
) {
    item {
        WeatherHeader(weather)
    }

    items(weather.hourlyForecastList, key = { it.time }) {
        HourlyForecastItem(hourlyForecast = it)
    }
}

@Composable
fun WeatherHeader(weather: Weather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = weather.city, fontSize = 18.sp)

        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text(text = "Wind", fontWeight = FontWeight.Bold)
                Text(text = weather.windDirection)
                Text(text = weather.windSpeed)
            }

            Column {
                Text(text = "Feels like: " + weather.feelsLike)
                Text(text = "${weather.highTemperature}/${weather.lowTemperature}")
            }
        }
    }
}

@Composable
private fun HourlyForecastItem(
    hourlyForecast: HourlyForecast,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Column {
            Text(hourlyForecast.time)
        }

        Spacer(Modifier.weight(1f))

        Text(hourlyForecast.temperature)

        Spacer(Modifier.width(8.dp))

        AsyncImage(
            model = hourlyForecast.weatherIconUrl,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun ErrorView(
    exception: Throwable?,
    retry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(text = stringResource(exception.errorMessageResId))

        Button(
            onClick = retry,
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val weather = Weather(
        city = "Los Angeles",
        feelsLike = "74°",
        highTemperature = "76°",
        lowTemperature = "71°",
        windSpeed = "1.99 mph",
        windDirection = "SW",
        hourlyForecastList = listOf(
            HourlyForecast("3:00 PM", "73° F", ""),
            HourlyForecast("6:00 PM", "77° F", ""),
            HourlyForecast("9:00 PM", "81° F", ""),
        )
    )

    WeatherTheme {
        HomeScreen(
            uiState = HomeUiState.Success(weather),
            getWeather = {}
        )
    }
}