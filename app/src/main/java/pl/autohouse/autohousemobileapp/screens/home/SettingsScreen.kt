package pl.autohouse.autohousemobileapp.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.autohouse.autohousemobileapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    ipAddress: String,
    onEditIpAddressClick: (newIpAddress: String) -> Unit,
    location: Pair<Double, Double>,
    onEditLocationClick: (latitude: Double, longitude: Double) -> Unit,
    cityName: String,
    onEditCityNameClick: (newCityName: String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            TitleSection(title = "Settings")
        }

        var openServerSettings by remember {
            mutableStateOf(false)
        }
        var openWeatherSettings by remember {
            mutableStateOf(false)
        }

        SettingsSection(
            onServerClick = { openServerSettings = true },
            onWeatherClick = { openWeatherSettings = true }
        )


//        if (openServerSettings) {
//            EditServerSettingsDialogItem(
//                ipAddress = ipAddress,
//                onCancelClick = {
//                    openServerSettings = false
//                },
//                onSetIpAddressClick = {
//                    onEditIpAddressClick(it)
//                    openServerSettings = false
//                }
//            )
//        }
        if (openWeatherSettings) {
            EditWeatherSettingsDialogItem(
                Location = location,
                onCancelClick = {
                    openWeatherSettings = false
                },
                onSetLocationClick = { latitude, longitude ->
                    onEditLocationClick(latitude, longitude)
                    openWeatherSettings = false
                },
                cityName= cityName,
                onEditCityNameClick = onEditCityNameClick
            )
        }
    }
}

@Composable
fun SettingsSection(
    onServerClick: (String) -> Unit,
    onWeatherClick: (newIpAddress: String) -> Unit
) {
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .clickable {
//            onServerClick("Default")
//        }
//        .padding(horizontal = 15.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            painter = painterResource(id = R.drawable.ic_outline_dns_24),
//            contentDescription = "Server",
//            modifier = Modifier
//                .size(34.dp)
//                .padding(horizontal = 5.dp)
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 10.dp, vertical = 15.dp)
//        ) {
//            Text(
//                text = "Local Server",
//                fontSize = MaterialTheme.typography.titleLarge.fontSize,
//                fontWeight = FontWeight.Bold,
//            )
//            Text(
//                text = "Set up IP Address of Raspberry Pi",
//                fontSize = MaterialTheme.typography.labelLarge.fontSize,
//                fontWeight = FontWeight.Normal
//            )
//
//        }
//    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onWeatherClick("Default")
        }
        .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_cloud_24),
            contentDescription = "Weather",
            modifier = Modifier
                .size(34.dp)
                .padding(horizontal = 5.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp)
        ) {
            Text(
                text = "Weather",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Set up Location for Weather Widget",
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Normal
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServerSettingsDialogItem(
    ipAddress: String,
    onCancelClick: (String) -> Unit,
    onSetIpAddressClick: (String) -> Unit
) {

    Dialog(
        onDismissRequest = { onCancelClick("Default") }
    ) {


        var newIpAddress by remember {
            mutableStateOf(ipAddress)
        }

        var filled by remember {
            mutableStateOf(false)
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = newIpAddress,
                    onValueChange = { newIpAddress = it },
                    label = { Text(text = "ipAddress:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { onCancelClick("Default") }) {
                        Text(text = "Cancel")
                    }


                    if (newIpAddress != "") {
                        filled = true
                    }

                    Button(
                        onClick = {
                            onSetIpAddressClick(newIpAddress)
                        },
                        enabled = filled
                    ) {
                        Text(text = "Set")
                    }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWeatherSettingsDialogItem(
    Location: Pair<Double, Double>,
    onCancelClick: (String) -> Unit,
    onSetLocationClick: (latitude: Double, longitude: Double) -> Unit,
    cityName: String,
    onEditCityNameClick: (newCityName: String) -> Unit
) {

    Dialog(
        onDismissRequest = { onCancelClick("Default") }
    ) {


        var newLatitude by remember {
            mutableStateOf(Location.first.toString())
        }
        var newLongitude by remember {
            mutableStateOf(Location.second.toString())
        }
        var newCityName by remember {
            mutableStateOf(cityName)
        }

        var filled by remember {
            mutableStateOf(false)
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = newLatitude,
                    onValueChange = { newLatitude = it },
                    label = { Text(text = "Latitude:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = newLongitude,
                    onValueChange = { newLongitude = it },
                    label = { Text(text = "Longitude:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text(text = "City Name:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { onCancelClick("Default") }) {
                        Text(text = "Cancel")
                    }


                    if (newLatitude != "" && newLatitude != "") {
                        filled = true
                    }

                    Button(
                        onClick = {
                            onSetLocationClick(newLatitude.toDouble(), newLongitude.toDouble())
                            onEditCityNameClick(newCityName)
                        },
                        enabled = filled
                    ) {
                        Text(text = "Set")
                    }
                }
            }

        }
    }

}

@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    SettingsScreen(
        "localhost",
        onEditIpAddressClick = {},
        location = Pair(0.00, 0.00),
        onEditLocationClick = { latitude, longitude -> },
        cityName = "",
        onEditCityNameClick = {}
    )
}