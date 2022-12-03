package pl.autohouse.autohousemobileapp.navigation

import pl.autohouse.autohousemobileapp.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val iconId: Int
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        iconId = R.drawable.ic_baseline_home_24

    )

    object Rooms : BottomBarScreen(
        route = "rooms",
        title = "Rooms",
        iconId = R.drawable.ic_baseline_sensor_door_24

    )

    object Scenes : BottomBarScreen(
        route = "scenes",
        title = "Scenes",
        iconId = R.drawable.ic_baseline_star_24
    )

    object Settings : BottomBarScreen(
        route = "settings",
        title = "Settings",
        iconId = R.drawable.ic_baseline_settings_24
    )
}
