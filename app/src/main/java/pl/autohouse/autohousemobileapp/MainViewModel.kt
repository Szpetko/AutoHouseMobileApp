package pl.autohouse.autohousemobileapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import pl.autohouse.autohousemobileapp.repository.Repository
import retrofit2.HttpException
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    var devices: MutableState<List<Device>> = mutableStateOf(listOf())

    var rooms: MutableState<List<Room>> = mutableStateOf(listOf())



    fun getDevices(){
        viewModelScope.launch {
            val response: List<Device> = try {
                repository.getDevices()
            }catch (e: HttpException){
                listOf()
            }

            devices.value = response
        }
    }

    fun getRooms(){
        viewModelScope.launch {
            val response: List<Room> = try {
                repository.getRooms()
            }catch (e: HttpException){
                listOf()
            }
            rooms.value = response
        }
    }

    fun toggleDevice(deviceId: Long){
        viewModelScope.launch {
            val response: Boolean = try {
                repository.toggleDevice(deviceId)
            }catch (e: HttpException){
                TODO()
            }
//            val device = devices.value.filter { device -> device.deviceId == deviceId }
//            device.forEach { dev -> dev.status = response}

        }
    }
}