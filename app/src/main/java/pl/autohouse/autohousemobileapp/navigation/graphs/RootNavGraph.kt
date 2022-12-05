package pl.autohouse.autohousemobileapp.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.autohouse.autohousemobileapp.MainViewModel
import pl.autohouse.autohousemobileapp.navigation.Graph
import pl.autohouse.autohousemobileapp.screens.home.MainHomeScreen


@Composable
fun RootNavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(
        navController = navController,
        //startDestination = Graph.START,
        startDestination = Graph.HOME,
        route = Graph.ROOT
    ) {
        //TODO("Add onBoarding Screens")
        //startNavGraph(navController = navController)
        composable(route = Graph.HOME) {
            MainHomeScreen(viewModel)
        }
    }
}

