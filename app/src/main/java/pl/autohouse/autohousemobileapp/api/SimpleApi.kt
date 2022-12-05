package pl.autohouse.autohousemobileapp.api

import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface SimpleApi {

    @GET("/api/v1/device")
    suspend fun getDevices(): List<Device>

    @GET("/api/v1/room")
    suspend fun getRooms(): List<Room>

    @PUT("/api/v1/device/{deviceId}/toggle")
    suspend fun toggleDevice(
        @Path("deviceId") deviceId: Long
    ):Boolean
}