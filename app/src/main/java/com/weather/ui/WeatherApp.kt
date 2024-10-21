package com.weather.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weather.core.designsystem.components.WeatherNavigationSuiteScaffold
import com.weather.navigation.TopLevelDestination
import com.weather.navigation.WeatherNavHost
import com.weather.navigation.navigateToTopLevelDestination

@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    WeatherNavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach { destination ->
                item(
                    selected = currentDestination?.route == destination.route,
                    label = { Text(destination.label) },
                    onClick = {
                        navController.navigateToTopLevelDestination(destination)
                    },
                    icon = {
                        Icon(destination.icon, null)
                    }
                )
            }
        }
    ) {
        WeatherNavHost(navController = navController)
    }
}