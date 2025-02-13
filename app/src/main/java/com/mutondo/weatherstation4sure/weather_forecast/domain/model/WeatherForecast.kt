package com.mutondo.weatherstation4sure.weather_forecast.domain.model

data class WeatherForecast(
    val temperature: String,
    val tempMax: String,
    val tempMin: String,
    val description: String,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val dayOfWeek: String,
    val timeStampString: String,
    val city: String,
    val sunrise: Long,
    val sunset: Long
)
