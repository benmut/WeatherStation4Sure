package com.mutondo.weatherstation4sure.navigation

const val LATITUDE_KEY = "latitude_key"
const val LONGITUDE_KEY = "longitude_key"

sealed class Destination(val route: String) {
    data object Map : Destination("map_screen")
    data object DaysForecast : Destination("days_forecast_screen")
}
