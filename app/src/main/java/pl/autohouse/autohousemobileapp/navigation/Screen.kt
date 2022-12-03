package pl.autohouse.autohousemobileapp.navigation

object Graph {
    const val ROOT = "root_graph"
    const val START = "start_graph"
    const val HOME = "home_graph"
}

sealed class Screen(val route: String) {
    object Start : Screen(route = "start_screen")
    object SetIpAddress : Screen(route = "setIpAddress_screen")

    object RoomsDetail : Screen(route = "roomsDetail_screen")
    object ScenesDetail : Screen(route = "scenesDetail_screen")
    object SettingsDetail : Screen(route = "settingsDetail_screen")
}


