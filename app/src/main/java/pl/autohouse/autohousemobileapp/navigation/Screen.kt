package pl.autohouse.autohousemobileapp.navigation

const val ROOMS_DETAIL_ARGUMENT_KEY = "roomId"


object Graph {
    const val ROOT = "root_graph"
    const val START = "start_graph"
    const val HOME = "home_graph"
}

sealed class Screen(val route: String) {
    object Start : Screen(route = "start_screen")
    object SetIpAddress : Screen(route = "setIpAddress_screen")

    object RoomsDetail : Screen(route = "roomsDetail_screen/{$ROOMS_DETAIL_ARGUMENT_KEY}")
}


