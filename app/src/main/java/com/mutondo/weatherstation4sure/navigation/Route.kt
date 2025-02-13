package com.mutondo.weatherstation4sure.navigation

sealed class Destination(val route: String) {
    data object Map : Destination("map_screen")
}
