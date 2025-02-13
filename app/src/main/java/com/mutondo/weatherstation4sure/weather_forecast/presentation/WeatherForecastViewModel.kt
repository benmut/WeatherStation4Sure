package com.mutondo.weatherstation4sure.weather_forecast.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mutondo.weatherstation4sure.common.Resource
import com.mutondo.weatherstation4sure.weather_forecast.domain.use_case.GetWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase
) : ViewModel() {

    private val _uiState = mutableStateOf(WeatherForecastUiState())
    val uiState: State<WeatherForecastUiState>
        get() = _uiState

    fun onEvent(event: WeatherForecastUiEvent) {
        when (event) {
            is WeatherForecastUiEvent.GetWeatherForecast -> {
                _uiState.value = _uiState.value.copy(isLoading = true)
                getWeatherForecast(
                    event.latitude,
                    event.longitude,
                    event.apiKey
                )
            }
        }
    }

    private fun getWeatherForecast(latitude: String, longitude: String, apiKey: String) {
        viewModelScope.launch {
            when (val resource = getWeatherForecastUseCase(latitude, longitude, apiKey)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = String(),
                        forecasts = resource.data.orEmpty()
                    )
                }

                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = resource.message.toString()
                    )
                }
            }
        }
    }
}
