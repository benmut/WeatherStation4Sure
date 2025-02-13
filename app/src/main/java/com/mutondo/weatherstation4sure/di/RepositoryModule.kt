package com.mutondo.weatherstation4sure.di

import com.mutondo.weatherstation4sure.weather_forecast.data.remote.repository.WeatherForecastRepositoryImpl
import com.mutondo.weatherstation4sure.weather_forecast.domain.repository.WeatherForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindWeatherForecastRepository(implementation: WeatherForecastRepositoryImpl): WeatherForecastRepository
}