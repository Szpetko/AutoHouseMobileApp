package pl.autohouse.autohousemobileapp.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pl.autohouse.autohousemobileapp.navigation.Graph
import pl.autohouse.autohousemobileapp.ui.theme.start.SetIPAddressScreen
import pl.autohouse.autohousemobileapp.ui.theme.start.StartScreen
import pl.autohouse.autohousemobileapp.navigation.Screen


fun NavGraphBuilder.startNavGraph(navController: NavController) {
    navigation(startDestination = Screen.Start.route, route = Graph.START) {
        composable(route = Screen.Start.route) {
            StartScreen(navController = navController)
        }
        composable(route = Screen.SetIpAddress.route) {
            SetIPAddressScreen(navController = navController)
        }
    }
}


