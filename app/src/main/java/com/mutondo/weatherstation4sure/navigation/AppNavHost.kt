package com.mutondo.weatherstation4sure.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mutondo.weatherstation4sure.map.MapScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = Destination.Map.route
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Map.route) {
            MapScreen()
        }
    }
}
