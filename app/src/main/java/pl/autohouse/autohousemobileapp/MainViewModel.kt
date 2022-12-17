package pl.autohouse.autohousemobileapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import pl.autohouse.autohousemobileapp.api.CurrentWeatherDataDto
import pl.autohouse.autohousemobileapp.api.WeatherDto
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import pl.autohouse.autohousemobileapp.repository.Repository
import pl.autohouse.autohousemobileapp.repository.SettingsRepository
import retrofit2.HttpException
import java.io.IOException

class MainViewModel(
    private val settingsRepository: SettingsRepository,
    private val repository: Repository
    ): ViewModel() {



    //Mutable Properties
    var devices: MutableState<List<Device>> = mutableStateOf(listOf())

    var rooms: MutableState<List<Room>> = mutableStateOf(listOf())

    var latitude: MutableState<Double> = mutableStateOf(0.00)

    val longitude: MutableState<Double> = mutableStateOf(0.00)

    val currentWeatherData: MutableState<WeatherDto> = mutableStateOf(WeatherDto(CurrentWeatherDataDto(0.00,0)))


    //DataStore

    //IpAddress
    val readIpAddress = settingsRepository.getIpAddress

    fun saveIpAddress(ipAddress: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsRepository.saveIpAddressDS(ipAddress = ipAddress)
    }

    //Location
    val readLocation = settingsRepository.getLocation

    fun saveLocation(latitude: Double, longitude:Double) = viewModelScope.launch(Dispatchers.IO) {
        settingsRepository.saveLocation(latitude = latitude, longitude = longitude)
    }

    //CityName
    val readCityName= settingsRepository.getCityName

    fun saveCityName(cityName: String) = viewModelScope.launch(Dispatchers.IO) {
        settingsRepository.saveCityName(cityName = cityName)
    }



    //Retrofit
    fun getDevices(){
        viewModelScope.launch {
            val response: List<Device> = try {
                repository.getDevices()
            }catch (e: HttpException){
                listOf()
            }catch (e: IOException){
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
            }catch (e: IOException){
                listOf()
            }
            rooms.value = response
        }
    }

    fun toggleDevice(deviceId: Long){
        viewModelScope.launch {
            try {
                repository.toggleDevice(deviceId)
            }catch (e: HttpException){
                //Do Nothing
            }catch (e: IOException){
                //Do Nothing
            }
        }
    }

    fun deleteDevice(deviceId: Long){
        viewModelScope.launch {
            try {
                repository.deleteDevice(deviceId)
            }catch (e: HttpException){
                //Do Nothing
            }catch (e: IOException){
                //Do Nothing
            }
        }
    }

    fun postDevice(device: Device){
        viewModelScope.launch{
            try {
                repository.postDevice(device)
            }catch (e: HttpException){
                //Do Nothing
            }catch (e: IOException){
                //Do Nothing
            }
        }
    }

    fun postRoom(room: Room){
        viewModelScope.launch{
            try {
                repository.postRoom(room)
            }catch (e: HttpException){
                //Do Nothing
            }catch (e: IOException){
                //Do Nothing
            }
        }
    }

    fun patchDevice(deviceId: Long, device: Device){
        viewModelScope.launch{
            try {
                repository.patchDevice(deviceId, device)
            }catch (e: HttpException){
                //Do Nothing
            }catch (e: IOException){
                //Do Nothing
            }
        }
    }

    fun patchRoom(roomId: Long, room: Room){
        viewModelScope.launch {
            try {
                repository.patchRoom(roomId, room)
            }catch (e: HttpException){
                //Do Nothing
            }catch (e: IOException){
                //Do Nothing
            }
        }
    }

    fun deleteRoom(roomId: Long){
        viewModelScope.launch {
            try {
                repository.deleteRoom(roomId)
            }catch (e: HttpException){
                //Do Nothing
            }catch (e: IOException){
                //Do Nothing
            }
        }
    }




    //Weather
    fun getWeatherData(latitude: Double, longitude: Double){
        viewModelScope.launch {
            val response: WeatherDto = try {
                repository.getWeatherData(latitude,longitude)
            }catch (e: HttpException){
                WeatherDto(CurrentWeatherDataDto(0.00,0))
            }catch (e: IOException){
                WeatherDto(CurrentWeatherDataDto(0.00,0))
            }

            currentWeatherData.value = response
        }
    }


}

