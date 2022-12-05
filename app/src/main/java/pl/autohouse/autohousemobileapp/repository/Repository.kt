package pl.autohouse.autohousemobileapp.repository

import pl.autohouse.autohousemobileapp.api.RetrofitInstance
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import retrofit2.Response

class Repository {

    suspend fun getDevices(): List<Device>{
        return RetrofitInstance.api.getDevices()
    }

    suspend fun getRooms(): List<Room>{
        return RetrofitInstance.api.getRooms()
    }

    suspend fun toggleDevice(deviceId: Long): Boolean {
        return RetrofitInstance.api.toggleDevice(deviceId)
    }
}