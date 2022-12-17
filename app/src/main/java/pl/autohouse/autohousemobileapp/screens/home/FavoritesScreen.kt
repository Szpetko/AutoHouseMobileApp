package pl.autohouse.autohousemobileapp.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.autohouse.autohousemobileapp.model.Device

@Composable
fun FavoritesScreen(
    devices: List<Device>?,
    onDeviceClick: (deviceId: Long) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ){
        
        TitleSection(title = "Favorites")
        
        if (devices != null && devices.isNotEmpty()) {
            val favouriteDeviceList = devices.filter { device -> device.isFavourite == true }
            DevicesSection(devices = favouriteDeviceList, onDeviceClick = onDeviceClick)
        } else {
            Text(
                text = "Failed to load data from the server",
                modifier = Modifier
                    .padding(40.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
    
}

@Composable
@Preview(showBackground = true)
fun ScenesScreenPreview() {

    val tempDeviceList =
        listOf(
            Device("device", 1, "Lampki", 1, false, 14, 1, false),
            Device("device", 2, "Lampki2", 2, false, 16, 1, true),
            Device("device", 1, "Lampki", 1, true, 14, 1, true),
            Device("device", 2, "Lampki2", 2, false, 16, 1, true),
            Device("device", 1, "Lampki", 1, false, 14, 2, false),
            Device("device", 2, "Lampki2", 2, false, 16, 2, false)
        )

    FavoritesScreen(tempDeviceList, {})
}