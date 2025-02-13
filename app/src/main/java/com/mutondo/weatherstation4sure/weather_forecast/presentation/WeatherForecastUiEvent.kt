package com.mutondo.weatherstation4sure.weather_forecast.presentation

sealed class WeatherForecastUiEvent {
    data class  GetWeatherForecast(
        val latitude: String,
        val longitude: String,
        val apiKey: String
    ) : WeatherForecastUiEvent()
}
