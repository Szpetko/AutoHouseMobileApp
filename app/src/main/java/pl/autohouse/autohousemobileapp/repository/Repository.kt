package pl.autohouse.autohousemobileapp.repository

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import pl.autohouse.autohousemobileapp.api.RetrofitInstance
import pl.autohouse.autohousemobileapp.api.WeatherDto
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import retrofit2.http.Url

class Repository() {


    suspend fun getDevices(): List<Device> {
        return RetrofitInstance.api.getDevices()
    }

    suspend fun getRooms(): List<Room> {
        return RetrofitInstance.api.getRooms()
    }

    suspend fun toggleDevice(deviceId: Long): Boolean {
        return RetrofitInstance.api.toggleDevice(deviceId)
    }

    suspend fun deleteDevice(deviceId: Long): Int {
        return RetrofitInstance.api.deleteDevice(deviceId = deviceId)
    }

    suspend fun postDevice(device: Device): Int {
        return RetrofitInstance.api.postDevice(device = device)
    }

    suspend fun postRoom(room: Room): Int {
        return RetrofitInstance.api.postRoom(room = room)
    }

    suspend fun patchDevice(deviceId: Long ,device: Device):Int{
        return RetrofitInstance.api.patchDevice(deviceId = deviceId, device = device)
    }

    suspend fun patchRoom(roomId: Long, room: Room):Int{
        return RetrofitInstance.api.patchRoom(roomId = roomId, room= room)
    }

    suspend fun deleteRoom(roomId: Long):Int{
        return RetrofitInstance.api.deleteRoom(roomId= roomId)
    }

    //Weather
    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherDto {
        return RetrofitInstance.weatherApi.getWeatherData(latitude,longitude)
    }
}