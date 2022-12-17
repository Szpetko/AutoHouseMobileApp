package pl.autohouse.autohousemobileapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/v1/forecast?current_weather=true")
    suspend fun getWeatherData(
        @Query("latitude") lat:Double,
        @Query("longitude") long: Double
    ):WeatherDto
}