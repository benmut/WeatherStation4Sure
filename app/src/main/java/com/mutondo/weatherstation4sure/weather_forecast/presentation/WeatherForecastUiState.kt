package com.mutondo.weatherstation4sure.weather_forecast.presentation

import com.mutondo.weatherstation4sure.weather_forecast.domain.model.WeatherForecast

data class WeatherForecastUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = String(),
    var forecasts: List<WeatherForecast> = emptyList()
)
