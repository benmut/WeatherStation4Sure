package com.mutondo.weatherstation4sure.weather_forecast.domain.model

data class WeatherForecast(
    val temperature: Float,
    val tempMax: Float,
    val tempMin: Float,
    val description: String,
    val icon: String,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val dayOfWeek: String,
    val timeStampString: String,
    val city: String,
    val sunrise: String,
    val sunset: String
)
