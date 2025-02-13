package com.mutondo.weatherstation4sure.weather_forecast.data.remote.repository

import com.mutondo.weatherstation4sure.common.Resource
import com.mutondo.weatherstation4sure.weather_forecast.data.remote.api_service.WeatherForecastService
import com.mutondo.weatherstation4sure.weather_forecast.data.remote.dto.toWeatherForecast
import com.mutondo.weatherstation4sure.weather_forecast.domain.model.WeatherForecast
import com.mutondo.weatherstation4sure.weather_forecast.domain.repository.WeatherForecastRepository
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherForecastRepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : WeatherForecastRepository {
    override suspend fun getForecast5Data(
        latitude: String,
        longitude: String,
        apiKey: String
    ): Resource<List<WeatherForecast>> {
        return try {
            val response = retrofit.create(WeatherForecastService::class.java)
                .getForecast5Data(latitude, longitude, apiKey)

            if (response?.code == HttpURLConnection.HTTP_OK) {
                Resource.Success(response.toWeatherForecast())
            } else {
                Resource.Error(response?.message.toString(), null)
            }

        } catch (e: HttpException) {
            return Resource.Error(e.message.toString(), null)
        }
    }
}
