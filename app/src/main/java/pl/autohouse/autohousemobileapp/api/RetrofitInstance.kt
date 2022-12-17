package pl.autohouse.autohousemobileapp.api

import android.util.Log
import pl.autohouse.autohousemobileapp.util.Constants.Companion.BASE_URL
import pl.autohouse.autohousemobileapp.util.Constants.Companion.BASE_WEATHER_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }

    private val retrofitWeather by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi:WeatherApi by lazy {
        retrofitWeather.create(WeatherApi::class.java)
    }
}