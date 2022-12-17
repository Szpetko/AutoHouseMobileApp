package pl.autohouse.autohousemobileapp.screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.plcoding.weatherapp.domain.weather.WeatherType
import kotlinx.coroutines.delay
import pl.autohouse.autohousemobileapp.api.CurrentWeatherDataDto
import pl.autohouse.autohousemobileapp.api.WeatherDto
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import pl.autohouse.autohousemobileapp.model.fromId
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun HomeScreen(
    devices: List<Device>?,
    rooms: List<Room>?,
    onDeviceClick: (deviceId: Long) -> Unit,
    cityName: String,
    currentWeather: WeatherDto
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {
        Column {
            TitleSection(title = "Home")
            WeatherItem(cityName = cityName, currentWeather= currentWeather)


            val favouriteDeviceList = devices!!.filter { device -> device.isFavourite == true }
            FavoriteSection(
                favouriteDevices = favouriteDeviceList,
                onFavouriteDeviceClick = onDeviceClick
            )


            if (rooms != null && rooms.isNotEmpty()) {

                RoomsSection(rooms = rooms, devices = devices, onDeviceClick)

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
fun WeatherItem(
    cityName: String,
    currentWeather: WeatherDto
) {

    val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    val time = remember {
        mutableStateOf(LocalTime.now().format(timeFormat))
    }

    val dateFormat = DateTimeFormatter.ofPattern("dd-MM")
    val date = LocalDate.now().format(dateFormat)
    val dayInWeek = LocalDate.now().dayOfWeek.name.substring(0, 3)

    val weatherType = WeatherType.fromWMO(currentWeather.currentWeatherData.weatherCode)


    LaunchedEffect(key1 = true) {
        while (true) {
            delay(5000)
            time.value = LocalTime.now().format(timeFormat)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(min = 300.dp, max = 500.dp)
        ) {
            Row(
                Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(verticalArrangement = Arrangement.Center) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            painter = painterResource(id = weatherType.iconRes),
                            contentDescription = "Sunny",
                            Modifier.size(26.dp)
                        )
                        Text(
                            text = weatherType.weatherDesc,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )

                    }

                    Text(
                        text = currentWeather.currentWeatherData.temperature.toString() + "°",
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
                    val format = DateTimeFormatter.ofPattern("HH:mm")
                    Text(
                        text = time.value,
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
                        text = "$dayInWeek $date",
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
                        text = cityName,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        }
    }


}


@Composable
fun FavoriteSection(
    favouriteDevices: List<Device>,
    onFavouriteDeviceClick: (deviceId: Long) -> Unit
) {

    Text(
        text = "Favorite",
        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        items(favouriteDevices.size) { device ->
            FavouriteItem(favouriteDevice = favouriteDevices[device], onFavouriteDeviceClick)
        }
    }

}

@Composable
fun FavouriteItem(
    favouriteDevice: Device,
    onClick: (deviceId: Long) -> Unit
) {

    val color =
        if (favouriteDevice.status) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors()
    val stateText = if (favouriteDevice.status) "On" else "Off"


    Card(
        modifier = Modifier
            .height(110.dp)
            .width(120.dp)
            .clickable {
                onClick(favouriteDevice.deviceId)
            },
        colors = color
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Text(
                text = favouriteDevice.name,
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
                    checked = favouriteDevice.status,
                    onCheckedChange = { favouriteDevice.status },
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
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = fromId(device.iconId)),
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
            Device("device", 1, "Lampki", 1, false, 14, 1, false),
            Device("device", 2, "Lampki2", 2, false, 16, 1, true),
            Device("device", 1, "Lampki", 1, true, 14, 1, true),
            Device("device", 2, "Lampki2", 2, false, 16, 1, true),
            Device("device", 1, "Lampki", 1, false, 14, 2, false),
            Device("device", 2, "Lampki2", 2, false, 16, 2, false)
        )

    val rooms = listOf(
        Room("room", 1, "Living Room", 1),
        Room("room", 2, "Kitchen", 1),
        Room("room", 3, "Some Room", 1),
        Room("room", 4, "Bed Room", 1)
    )


    HomeScreen(tempDeviceList, rooms, {}, "", WeatherDto(
        CurrentWeatherDataDto(0.00,0))
    )
}
