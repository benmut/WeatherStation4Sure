package com.mutondo.weatherstation4sure.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mutondo.weatherstation4sure.map.MapScreen
import com.mutondo.weatherstation4sure.weather_forecast.presentation.DaysForecastScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = Destination.Map.route
) {
    val navController = rememberNavController()
    val navigator = NavigatorImpl(navController)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Map.route) {
            MapScreen(navigator = navigator)
        }
        composable(
            route = "${Destination.DaysForecast.route}/{$LATITUDE_KEY}/{$LONGITUDE_KEY}",
            arguments = listOf(
                navArgument(LATITUDE_KEY) {
                    type = NavType.StringType
                },
                navArgument(LONGITUDE_KEY) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            DaysForecastScreen(
                navigator = navigator,
                navBackStackEntry = navBackStackEntry
            )
        }
    }
}
