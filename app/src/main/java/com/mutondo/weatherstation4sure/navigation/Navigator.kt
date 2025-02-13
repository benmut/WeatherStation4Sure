package com.mutondo.weatherstation4sure.navigation

interface Navigator {
    fun navigateUp()
    fun navigateToDaysForecast(latitude: String, longitude: String)
}
