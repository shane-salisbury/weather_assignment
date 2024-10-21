package com.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weather.feature.forecast.ForecastScreen
import com.weather.feature.home.HomeScreen

@Composable
fun WeatherNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.Home.route,
        modifier = modifier
    ) {
        composable(route = TopLevelDestination.Home.route) {
            HomeScreen()
        }

        composable(route = TopLevelDestination.Forecast.route) {
            ForecastScreen()
        }
    }
}