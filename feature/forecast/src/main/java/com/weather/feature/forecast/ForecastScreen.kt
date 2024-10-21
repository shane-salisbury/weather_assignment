package com.weather.feature.forecast

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment

@Composable
fun ForecastScreen() {
    AndroidFragment<ForecastFragment>(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    )
}