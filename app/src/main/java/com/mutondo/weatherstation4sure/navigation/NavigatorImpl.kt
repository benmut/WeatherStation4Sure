package com.mutondo.weatherstation4sure.navigation

import androidx.navigation.NavController

class NavigatorImpl(
    private val navController: NavController
) : Navigator {
    override fun navigateUp() {
        navController.popBackStack()
    }

    override fun navigateToDaysForecast(latitude: String, longitude: String) {
        navController.navigate("${Destination.DaysForecast.route}/$latitude/$longitude")
    }
}