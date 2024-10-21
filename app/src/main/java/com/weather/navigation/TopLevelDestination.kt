package com.weather.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions

enum class TopLevelDestination(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    Home("home", Icons.Default.Home, "Home"),
    Forecast("forecast", Icons.Default.CalendarToday, "Forecast")
}

fun NavHostController.navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
    val navOptions = navOptions {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
    navigate(topLevelDestination.route, navOptions)
}