package com.weather.core.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun WeatherNavigationSuiteScaffold(
    navigationSuiteItems: WeatherNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            unselectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.Black,
            indicatorColor = Color.Transparent,
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = Color.White,
            unselectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.Black,
            indicatorColor = Color.Transparent,
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = Color.White,
            unselectedIconColor = Color.Black,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.Black,
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            WeatherNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier,
        content = content
    )
}

class WeatherNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        icon: @Composable () -> Unit,
        modifier: Modifier = Modifier,
        label: @Composable (() -> Unit),
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}