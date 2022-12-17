package pl.autohouse.autohousemobileapp.api

import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import retrofit2.Response
import retrofit2.http.*

interface SimpleApi {

    @GET("/api/v1/device")
    suspend fun getDevices(): List<Device>

    @GET("/api/v1/room")
    suspend fun getRooms(): List<Room>

    @PUT("/api/v1/device/{deviceId}/toggle")
    suspend fun toggleDevice(
        @Path("deviceId") deviceId: Long
    ):Boolean

    @DELETE("/api/v1/device/{deviceId}")
    suspend fun deleteDevice(
        @Path("deviceId") deviceId: Long
    ):Int

    @POST("/api/v1/device")
    suspend fun postDevice(
        @Body device: Device
    ):Int

    @POST("/api/v1/room")
    suspend fun postRoom(
        @Body room: Room
    ):Int

    @PATCH("/api/v1/device/{deviceId}")
    suspend fun patchDevice(
        @Path("deviceId") deviceId: Long,
        @Body device: Device
    ):Int

    @PATCH("/api/v1/room/{roomId}")
    suspend fun patchRoom(
        @Path("roomId") roomId: Long,
        @Body room: Room
    ):Int

    @DELETE("/api/v1/room/{roomId}")
    suspend fun deleteRoom(
        @Path("roomId") roomId: Long
    ):Int
}