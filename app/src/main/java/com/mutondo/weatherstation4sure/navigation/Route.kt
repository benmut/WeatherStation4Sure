package com.mutondo.weatherstation4sure.navigation

const val LATITUDE_KEY = "latitude_key"
const val LONGITUDE_KEY = "longitude_key"
const val DAY_INDEX_KEY = "day_index_key"

sealed class Destination(val route: String) {
    data object Map : Destination("map_screen")
    data object DaysForecast : Destination("days_forecast_screen")
    data object DayForecastDetails : Destination("day_forecast_details_screen")
}
