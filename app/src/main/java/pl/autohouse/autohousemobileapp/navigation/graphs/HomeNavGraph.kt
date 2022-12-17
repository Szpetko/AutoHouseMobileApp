package pl.autohouse.autohousemobileapp.navigation.graphs

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.autohouse.autohousemobileapp.MainViewModel
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import pl.autohouse.autohousemobileapp.navigation.BottomBarScreen
import pl.autohouse.autohousemobileapp.navigation.Graph
import pl.autohouse.autohousemobileapp.navigation.ROOMS_DETAIL_ARGUMENT_KEY
import pl.autohouse.autohousemobileapp.navigation.Screen
import pl.autohouse.autohousemobileapp.screens.home.*
import pl.autohouse.autohousemobileapp.screens.home.details.Rooms_DetailScreen
import java.io.IOException
import java.util.*

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {


    val devicesResponse = viewModel.devices.value
    val roomsResponse = viewModel.rooms.value
    val currentWeatherDataResponse = viewModel.currentWeatherData.value

    val ipAddress = viewModel.readIpAddress.collectAsState(initial = "localhost:8080")
    val location = viewModel.readLocation.collectAsState(initial = Pair(0.00, 0.00))
    val cityName = viewModel.readCityName.collectAsState(initial = "none")




    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        route = Graph.START
    ) {


        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                devices = devicesResponse,
                rooms = roomsResponse,
                onDeviceClick = { deviceId: Long ->
                    runBlocking {
                        launch {
                            delay(50L)
                            viewModel.getDevices()
                        }
                        viewModel.toggleDevice(deviceId)
                    }
                },
                cityName = cityName.value,
                currentWeather = currentWeatherDataResponse
            )
            viewModel.getWeatherData(location.value.first, location.value.second)
        }

        composable(route = BottomBarScreen.Rooms.route) {
            RoomsScreen(
                rooms = roomsResponse,
                onRoomCardClick = { roomId ->
                    navController.navigate(route = "roomsDetail_screen/$roomId")
                },
                onAddRoomClick = { room: Room ->
                    runBlocking {
                        launch {
                            delay(300L)
                            viewModel.getRooms()
                        }
                        viewModel.postRoom(room)
                    }
                },
                onChangeRoomClick = { room: Room ->
                    runBlocking {
                        launch {
                            delay(250L)
                            viewModel.getRooms()
                        }
                        viewModel.patchRoom(room.roomId, room)
                    }
                },
                onDeleteClick = { roomId: Long ->
                    runBlocking {
                        launch {
                            delay(250L)
                            viewModel.getRooms()
                        }
                        viewModel.deleteRoom(roomId)
                    }
                }
            )
        }

        composable(route = BottomBarScreen.Favorites.route) {
            FavoritesScreen(
                devices = devicesResponse,
                onDeviceClick = { deviceId: Long ->
                    runBlocking {
                        launch {
                            delay(100L)
                            viewModel.getDevices()
                        }
                        viewModel.toggleDevice(deviceId)
                    }
                }
            )
        }

        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(
                ipAddress = ipAddress.value,
                onEditIpAddressClick = { newIpAddress ->
                    viewModel.saveIpAddress(newIpAddress)
                },
                location = location.value,
                onEditLocationClick = { latitude, longitude ->
                    viewModel.saveLocation(latitude, longitude)
                },
                cityName = cityName.value,
                onEditCityNameClick = {
                    viewModel.saveCityName(it)

                }
            )
        }

        composable(
            route = Screen.RoomsDetail.route,
            arguments = listOf(navArgument(ROOMS_DETAIL_ARGUMENT_KEY) {
                type = NavType.LongType
            })
        ) {
            Rooms_DetailScreen(
                devices = devicesResponse, it.arguments?.getLong(ROOMS_DETAIL_ARGUMENT_KEY),
                onBackArrowClick = { navController.navigateUp() },
                onDeleteClick = { deviceId: Long ->
                    runBlocking {
                        launch {
                            delay(250L)
                            viewModel.getDevices()
                        }
                        viewModel.deleteDevice(deviceId)
                    }
                },
                onToggleClick = { deviceId: Long ->
                    runBlocking {
                        launch {
                            delay(100L)
                            viewModel.getDevices()
                        }
                        viewModel.toggleDevice(deviceId)
                    }
                },
                onChangeClick = { device: Device ->
                    runBlocking {
                        launch {
                            delay(250L)
                            viewModel.getDevices()
                        }
                        viewModel.patchDevice(device.deviceId, device)
                    }
                },
                onAddDeviceClick = { device: Device ->
                    runBlocking {
                        launch {
                            delay(250L)
                            viewModel.getDevices()
                        }
                        viewModel.postDevice(device)
                    }
                }
            )
        }
    }
}


