package com.mutondo.weatherstation4sure.weather_forecast.domain.repository

import com.mutondo.weatherstation4sure.common.Resource
import com.mutondo.weatherstation4sure.weather_forecast.domain.model.WeatherForecast

interface WeatherForecastRepository {
    suspend fun getForecast5Data(latitude: String, longitude: String, apiKey: String)
    : Resource<List<WeatherForecast>>
}
