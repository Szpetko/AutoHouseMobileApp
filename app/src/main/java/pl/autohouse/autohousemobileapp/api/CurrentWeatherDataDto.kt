package pl.autohouse.autohousemobileapp.api

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class CurrentWeatherDataDto(
    @SerializedName("temperature")
    val temperature: Double,
    @SerializedName("weathercode")
    val weatherCode: Int
)
