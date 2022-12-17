package pl.autohouse.autohousemobileapp.api

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("current_weather")
    val currentWeatherData: CurrentWeatherDataDto
)