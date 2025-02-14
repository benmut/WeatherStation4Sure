package com.mutondo.weatherstation4sure.weather_forecast.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mutondo.weatherstation4sure.utils.DayTimeUtils.Companion.getDayOfWeek
import com.mutondo.weatherstation4sure.utils.DayTimeUtils.Companion.getTimeFromTimestamp
import com.mutondo.weatherstation4sure.utils.orZero
import com.mutondo.weatherstation4sure.weather_forecast.domain.model.WeatherForecast

data class WeatherForecastDto(
    @SerializedName("cod")
    val code: Int? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("cnt")
    val count: Int? = null,
    @SerializedName("list")
    val forecasts: List<Forecast>? = listOf(),
    @SerializedName("city")
    val city: City? = null
)

data class Forecast(
    @SerializedName("dt")
    val timeStampL: Long? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("weather")
    val weathers: List<Weather>? = null,
    @SerializedName("visibility")
    val visibility: Int? = null,
    @SerializedName("dt_txt")
    val timeStampS: String? = null,
)

data class Main(
    @SerializedName("temp")
    val temperature: Float? = null,
    @SerializedName("temp_min")
    val temperatureMin: Float? = null,
    @SerializedName("temp_max")
    val temperatureMax: Float? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,
    @SerializedName("humidity")
    val humidity: Int? = null,
)

data class Weather(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("main")
    val main: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("icon")
    val icon: String? = null
)

data class City(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("sunrise")
    val sunrise: Long? = null,
    @SerializedName("sunset")
    val sunset: Long? = null,
)

fun WeatherForecastDto.toWeatherForecast(): List<WeatherForecast> {
    return forecasts?.filter { forecast ->
        // I choose to pick 12:00:00 data... I could pick based on current time as well
        forecast.timeStampS!!.contains("12:00:00") }?.map {
        WeatherForecast(
            temperature = it.main?.temperature.orZero,
            tempMax = it.main?.temperatureMax.orZero,
            tempMin = it.main?.temperatureMin.orZero,
            description = it.weathers?.get(0)?.description.orEmpty(),
            pressure = it.main?.pressure.orZero,
            humidity = it.main?.humidity.orZero,
            visibility = it.visibility.orZero / 1000,
            dayOfWeek = getDayOfWeek(it.timeStampL.orZero),
            timeStampString = it.timeStampS.toString(),
            city = city?.name.orEmpty(),
            sunrise = getTimeFromTimestamp(city?.sunrise.orZero),
            sunset = getTimeFromTimestamp(city?.sunset.orZero)
        )
    } ?: listOf()
}
