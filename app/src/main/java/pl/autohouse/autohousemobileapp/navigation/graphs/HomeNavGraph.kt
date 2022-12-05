package pl.autohouse.autohousemobileapp.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import pl.autohouse.autohousemobileapp.MainViewModel
import pl.autohouse.autohousemobileapp.navigation.BottomBarScreen
import pl.autohouse.autohousemobileapp.navigation.Graph
import pl.autohouse.autohousemobileapp.navigation.Screen
import pl.autohouse.autohousemobileapp.screens.home.*
import pl.autohouse.autohousemobileapp.screens.home.details.Rooms_DetailScreen
import pl.autohouse.autohousemobileapp.screens.home.details.Scenes_DetailScreen
import pl.autohouse.autohousemobileapp.screens.home.details.Settings_DetailScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel) {




    val devicesResponse = viewModel.devices.value
    val roomsResponse = viewModel.rooms.value

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        route = Graph.START
    ) {

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController, devices = devicesResponse, rooms = roomsResponse, onDeviceClick = {
                deviceId: Long -> viewModel.toggleDevice(deviceId)
                viewModel.getDevices()
            })
        }

        composable(route = BottomBarScreen.Rooms.route) {
            RoomsScreen(navController = navController)
        }

        composable(route = BottomBarScreen.Scenes.route) {
            ScenesScreen(navController = navController)
        }

        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        composable(route = Screen.RoomsDetail.route) {
            Rooms_DetailScreen(navController = navController)
        }

        composable(route = Screen.ScenesDetail.route) {
            Scenes_DetailScreen(navController = navController)
        }

        composable(route = Screen.SettingsDetail.route) {
            Settings_DetailScreen(navController = navController)
        }
    }
}


