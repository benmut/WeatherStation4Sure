package com.mutondo.weatherstation4sure.weather_forecast.data.remote.api_service

import com.mutondo.weatherstation4sure.weather_forecast.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {
    @GET("forecast")
    suspend fun getForecast5Data(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("apiKey") apiKey: String): WeatherForecastDto?
}
