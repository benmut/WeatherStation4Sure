package com.mutondo.weatherstation4sure.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mutondo.weatherstation4sure.map.MapScreen
import com.mutondo.weatherstation4sure.weather_forecast.presentation.DayForecastDetailsScreen
import com.mutondo.weatherstation4sure.weather_forecast.presentation.DaysForecastScreen
import com.mutondo.weatherstation4sure.weather_forecast.presentation.WeatherForecastViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = Destination.Map.route
) {
    val navController = rememberNavController()
    val navigator = NavigatorImpl(navController)
    val viewModel: WeatherForecastViewModel = hiltViewModel()

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
                navBackStackEntry = navBackStackEntry,
                viewModel = viewModel
            )
        }
        composable(
            route = "${Destination.DayForecastDetails.route}/{$DAY_INDEX_KEY}",
            arguments = listOf(
                navArgument(DAY_INDEX_KEY) {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            DayForecastDetailsScreen(
                navigator = navigator,
                navBackStackEntry = navBackStackEntry,
                viewModel = viewModel
            )
        }
    }
}
