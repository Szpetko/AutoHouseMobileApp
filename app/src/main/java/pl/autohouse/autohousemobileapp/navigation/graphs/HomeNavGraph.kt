package pl.autohouse.autohousemobileapp.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.autohouse.autohousemobileapp.navigation.BottomBarScreen
import pl.autohouse.autohousemobileapp.navigation.Graph
import pl.autohouse.autohousemobileapp.navigation.Screen
import pl.autohouse.autohousemobileapp.screens.home.HomeScreen
import pl.autohouse.autohousemobileapp.screens.home.RoomsScreen
import pl.autohouse.autohousemobileapp.screens.home.ScenesScreen
import pl.autohouse.autohousemobileapp.screens.home.SettingsScreen
import pl.autohouse.autohousemobileapp.screens.home.details.Rooms_DetailScreen
import pl.autohouse.autohousemobileapp.screens.home.details.Scenes_DetailScreen
import pl.autohouse.autohousemobileapp.screens.home.details.Settings_DetailScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        route = Graph.START
    ) {

        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController)
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


