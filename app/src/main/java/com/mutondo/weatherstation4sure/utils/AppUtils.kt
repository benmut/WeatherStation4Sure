package com.mutondo.weatherstation4sure.utils

import kotlin.math.roundToInt

class AppUtils {
    companion object {

        fun convertKelvinToCelsius(kelvin: Float): String {
            return (kelvin - 273.15f).roundToInt().toString() + "\u00B0C"
        }

        fun convertKelvinToFahrenheit(kelvin: Float): String {
            return ((kelvin - 273.15f) * 9/5 + 32).roundToInt().toString() + "\u00B0F"
        }
    }
}
