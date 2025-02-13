package com.mutondo.weatherstation4sure.weather_forecast.domain.use_case

import com.mutondo.weatherstation4sure.common.Resource
import com.mutondo.weatherstation4sure.weather_forecast.domain.model.WeatherForecast
import com.mutondo.weatherstation4sure.weather_forecast.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository
) {
    suspend operator fun invoke(
        latitude: String,
        longitude: String,
        apiKey: String
    ): Resource<List<WeatherForecast>> {
        return weatherForecastRepository.getForecast5Data(latitude, longitude, apiKey)
    }
}
