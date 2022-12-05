package pl.autohouse.autohousemobileapp.screens.home


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.autohouse.autohousemobileapp.R
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import pl.autohouse.autohousemobileapp.model.SceneItem
import retrofit2.Response

@Composable
fun HomeScreen(
    navController: NavController,
    devices: List<Device>?,
    rooms: List<Room>?,
    onDeviceClick: (deviceId: Long) -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Column {
            TitleSection(title = "Home")
            WeatherItem()

            val sceneList = listOf(
                SceneItem("Party Mode", false),
                SceneItem("Film", true),
                SceneItem("Cooking", false),
                SceneItem("Party Mode", false),
                SceneItem("Film", true),
                SceneItem("Cooking", false)
            )
            ScenesSection(sceneItems = sceneList)


//            val rooms = listOf(
//                Room("room", 1, "Living Room", 1),
//                Room("room", 2, "Kitchen", 1),
//                Room("room", 3, "Some Room", 1),
//                Room("room", 4, "Bed Room", 1)
//            )


            
            if (rooms != null && devices != null) {


                if (rooms.isNotEmpty()) {
                    //Text(text = devices.get(0).status.toString())
                    RoomsSection(rooms = rooms, devices = devices, onDeviceClick)
                }
            } else {
                Text(
                    text = "Failed to load data from the server",
                    modifier = Modifier
                        .padding(40.dp)
                        .align(CenterHorizontally)
                )
            }


        }
    }
}


@Composable
fun TitleSection(
    title: String
) {
    Text(
        text = title,
        fontSize = MaterialTheme.typography.displaySmall.fontSize,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 15.dp)
    )
}

@Composable
fun WeatherItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.sunny_icon),
                            contentDescription = "Sunny",
                            Modifier.size(26.dp)
                        )
                        Text(
                            text = "Sunny",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )

                    }

                    Text(
                        text = "30" + "°",
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    )

                }
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Top) {
                    Text(
                        text = "12:35",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )

                    )
                    Text(
                        text = "FRI  10-11",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cracow",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


}


@Composable
fun ScenesSection(sceneItems: List<SceneItem>) {

    Text(
        text = "Scenes",
        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        items(sceneItems.size) { scene ->
            SceneItem(sceneItem = sceneItems[scene])
        }
    }

}

@Composable
fun SceneItem(
    sceneItem: SceneItem,
) {
    val state = remember {
        mutableStateOf(sceneItem.status)
    }

    val color =
        if (state.value) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors()
    val stateText = if (state.value) "On" else "Off"


    Card(
        modifier = Modifier
            .height(110.dp)
            .width(120.dp)
            .clickable {
                state.value = !state.value
            },
        colors = color
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = sceneItem.title,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                ),
                modifier = Modifier.align(Alignment.TopStart)
            )
            Row(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stateText,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    ),
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .offset(x = 22.dp, y = 14.dp)
                )

                Switch(
                    checked = state.value,
                    onCheckedChange = { state.value = it },
                    modifier = Modifier
                        .scale(0.6f)
                        .offset(x = 18.dp, y = 24.dp)
                )
            }
        }
    }
}

@Composable
fun DevicesSection(
    devices: List<Device>,
    onDeviceClick: (deviceId: Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 90.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        items(devices.size) { device ->
            DeviceItem(device = devices[device], onClick = onDeviceClick)
        }
    }

}

@Composable
fun DeviceItem(
    device: Device,
    onClick: (deviceId: Long) -> Unit
) {

//    val state = remember {
//        mutableStateOf(device.status)
//    }


    val color =
        if (device.status) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors()
    val stateText = if (device.status) "On" else "Off"



    Card(
        modifier = Modifier
            .height(110.dp)
            .defaultMinSize(160.dp)
            .clickable {
                //device.status = !device.status
                onClick(device.deviceId)
            },
        colors = color
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = device.name,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                ),
                modifier = Modifier.align(Alignment.TopStart)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sunny_icon),
                    contentDescription = "Sunny",
                    Modifier
                        .size(height = 60.dp, width = 60.dp)
                        .padding(5.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stateText,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            )
                        ),
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .offset(x = 22.dp, y = 14.dp)
                    )

                    Switch(
                        checked = device.status,
                        onCheckedChange = { device.status = it },
                        modifier = Modifier
                            .scale(0.6f)
                            .offset(x = 18.dp, y = 24.dp)
                    )
                }

            }
        }
    }

}

@Composable
fun RoomsSection(
    rooms: List<Room>,
    devices: List<Device>,
    onDeviceClick: (deviceId: Long) -> Unit
) {

    var selectedRoom = remember {
        mutableStateOf(rooms[0].roomId)
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier.padding(bottom = 15.dp, top = 25.dp)
    ) {
        items(rooms.size) { room ->
            Row(
                modifier = Modifier.clickable { selectedRoom.value = rooms[room].roomId }
            ) {
                Room(
                    selectedItem = selectedRoom.value == rooms[room].roomId,
                    name = rooms[room].name
                )
            }
        }
    }


    val specificDeviceList = devices.filter { device -> device.roomId == selectedRoom.value }
    DevicesSection(devices = specificDeviceList, onDeviceClick)

}

@Composable
fun RowScope.Room(
    selectedItem: Boolean,
    name: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = name,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                )
            )
        )

        val color =
            if (selectedItem) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onBackground

        Box(
            modifier = Modifier
                .size(width = 45.dp, height = 10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(color),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {

    val tempDeviceList =
        listOf(
            Device("device", 1, "Lampki", 1, false, 14, 1),
            Device("device", 2, "Lampki2", 2, false, 16, 1),
            Device("device", 1, "Lampki", 1, false, 14, 1),
            Device("device", 2, "Lampki2", 2, false, 16, 1),
            Device("device", 1, "Lampki", 1, false, 14, 2),
            Device("device", 2, "Lampki2", 2, false, 16, 2)
        )

    val rooms = listOf(
        Room("room", 1, "Living Room", 1),
        Room("room", 2, "Kitchen", 1),
        Room("room", 3, "Some Room", 1),
        Room("room", 4, "Bed Room", 1)
    )


    HomeScreen(navController = rememberNavController(), tempDeviceList, rooms, {})
}

//@Preview
//@Composable
//fun RoomPreview() {
//    Room("Living Room", "Living Room")
//}

//@Preview
//@Composable
//fun WeatherItemPreview() {
//    WeatherItem()
//}
//
//
//
//@Preview
//@Composable
//fun SceneItemPreview() {
//    val newScene = SceneItem("Party Mode", false)
//    SceneItem(sceneItem = newScene)
//}