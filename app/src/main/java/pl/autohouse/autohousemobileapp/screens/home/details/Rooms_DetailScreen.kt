package pl.autohouse.autohousemobileapp.screens.home.details

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pl.autohouse.autohousemobileapp.R
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.fromId
import pl.autohouse.autohousemobileapp.model.iconIdList

@Composable
fun Rooms_DetailScreen(
    devices: List<Device>?,
    roomId: Long?,
    onBackArrowClick: (String) -> Unit,
    onToggleClick: (deviceId: Long) -> Unit,
    onDeleteClick: (deviceId: Long) -> Unit,
    onChangeClick: (device: Device) -> Unit,
    onAddDeviceClick: (device: Device) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {

        //Mutable States to open Dialogs
        var openEditDialog = remember {
            mutableStateOf(false)
        }
        var openAddDialog = remember {
            mutableStateOf(false)
        }

        //Temp mutable Device for Edit Device Dialog
        var device by remember {
            mutableStateOf(Device("None", 0, "None", 0, false, 0, 0, false))
        }

        //List of Devices with specific roomId
        val specificDeviceList = devices?.filter { device -> device.roomId == roomId }

        //Title with Back and Add Buttons
        TitleBackPlusSection(
            title = "Title",
            onAddRoomClick = {
                openAddDialog.value = true
            },
            onBackArrowClick = onBackArrowClick
        )


        //Display Devices
        DevicesSection(
            devices = specificDeviceList!!,
            onAddFavoritesClick = { device ->
                val tempDevice = Device(
                    device.type,
                    device.deviceId,
                    device.name,
                    device.iconId,
                    device.status,
                    device.pinAddress,
                    device.roomId,
                    true
                )
                onChangeClick(tempDevice)
            },
            onDeleteFavoritesClick = { device ->
                val tempDevice = Device(
                    device.type,
                    device.deviceId,
                    device.name,
                    device.iconId,
                    device.status,
                    device.pinAddress,
                    device.roomId,
                    false
                )
                onChangeClick(tempDevice)
            },
            onToggleClick = onToggleClick,
            onEditClick = {
                device = it
                openEditDialog.value = true
            },
            onDeleteClick = onDeleteClick,
        )

        //Open edit Device dialog
        if (openEditDialog.value) {
            EditDialogItem(
                device = device,
                onCancelClick = { openEditDialog.value = false },
                onChangeClick = {
                    openEditDialog.value = false
                    onChangeClick(it)
                }
            )
        }

        //Open add Device dialog
        if (openAddDialog.value) {
            AddDialogItem(
                roomId = roomId!!,
                onCancelClick = { openAddDialog.value = false },
                onAddClick = {
                    openAddDialog.value = false
                    onAddDeviceClick(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EditDialogItem(
    device: Device,
    onCancelClick: (String) -> Unit,
    onChangeClick: (device: Device) -> Unit
) {

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onCancelClick("Default") }
    ) {

        //Mutable States to change Parameters
        var newDeviceId by remember {
            mutableStateOf(device.deviceId.toString())
        }
        var newDeviceName by remember {
            mutableStateOf(device.name)
        }
        var newDeviceIconId by remember {
            mutableStateOf(device.iconId.toString())
        }
        var newDevicePinAddress by remember {
            mutableStateOf(device.pinAddress.toString())
        }
        var newRoomId by remember {
            mutableStateOf(device.roomId.toString())
        }

        //Mutable State to open Expansion
        var expandCard by remember {
            mutableStateOf(false)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .padding(horizontal = 35.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = newDeviceId,
                    onValueChange = { newDeviceId = it },
                    label = { Text(text = "deviceId:") },
                    enabled = false,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = newDeviceName,
                    onValueChange = { newDeviceName = it },
                    label = { Text(text = "Name:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                TextField(
                    value = newDeviceIconId,
                    onValueChange = { newDeviceIconId = it },
                    label = { Text(text = "iconId:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = newDevicePinAddress,
                    onValueChange = { newDevicePinAddress = it },
                    label = { Text(text = "pinAddress:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = newRoomId,
                    onValueChange = { newRoomId = it },
                    label = { Text(text = "roomId:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
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
                    Button(onClick = {

                        //Create and pass new Device
                        var newDevice = Device(
                            device.type,
                            newDeviceId.toLong(),
                            newDeviceName,
                            newDeviceIconId.toLong(),
                            device.status,
                            newDevicePinAddress.toInt(),
                            newRoomId.toLong(),
                            device.isFavourite
                        )
                        onChangeClick(newDevice)
                    }
                    ) {
                        Text(text = "Change")
                    }
                }

                //Icon id display
                IconButton(onClick = { expandCard = !expandCard }) {
                    if (expandCard) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                            contentDescription = "Expand Arrow Up"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                            contentDescription = "Expand Arrow Down"
                        )
                    }
                }
                if (expandCard) {
                    Divider()
                    Text(
                        text = "iconId:", modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp)
                            .align(Alignment.Start)
                    )


                    //Display list of icons in "parts" many columns
                    val parts = 2
                    val size = (iconIdList.size / parts) + (iconIdList.size % 2)

                    var subLists: List<List<Long>> = iconIdList.chunked(size)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        subLists.forEach() {
                            Column {
                                for (id in it) {
                                    Row(modifier = Modifier.padding(top = 2.dp)) {
                                        Text(
                                            text = "$id -->",
                                            modifier = Modifier.padding(end = 5.dp)
                                        )
                                        Icon(
                                            painter = painterResource(id = fromId(id)),
                                            contentDescription = "Icon $id"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddDialogItem(
    roomId: Long,
    onCancelClick: (String) -> Unit,
    onAddClick: (device: Device) -> Unit
) {

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onCancelClick("Default") }
    ) {

        //Mutable States to change Parameters
        var newDeviceName by remember {
            mutableStateOf("")
        }
        var newDeviceIconId by remember {
            mutableStateOf("")
        }
        var newDevicePinAddress by remember {
            mutableStateOf("")
        }
        var newRoomId by remember {
            mutableStateOf(roomId.toString())
        }
        var filled by remember {
            mutableStateOf(false)
        }


        //Mutable State to open Expansion
        var expandCard by remember {
            mutableStateOf(false)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .padding(horizontal = 35.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = newDeviceName,
                    onValueChange = { newDeviceName = it },
                    label = { Text(text = "Name:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                TextField(
                    value = newDeviceIconId,
                    onValueChange = { newDeviceIconId = it },
                    label = { Text(text = "iconId:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = newDevicePinAddress,
                    onValueChange = { newDevicePinAddress = it },
                    label = { Text(text = "pinAddress:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    )
                )
                TextField(
                    value = newRoomId,
                    onValueChange = { newRoomId = it },
                    label = { Text(text = "roomId:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    enabled = false
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { onCancelClick("Default") }) {
                        Text(text = "Cancel")
                    }


                    if (newDeviceName != "" && newDeviceIconId != "" && newDevicePinAddress != "" && newRoomId != "") {
                        filled = true
                    }

                    Button(
                        onClick = {

                            //Create and pass new Device
                            var newDevice = Device(
                                "device",
                                0,
                                newDeviceName,
                                newDeviceIconId.toLong(),
                                false,
                                newDevicePinAddress.toInt(),
                                roomId,
                                false
                            )
                            onAddClick(newDevice)
                        },
                        enabled = filled
                    ) {
                        Text(text = "Add")
                    }
                }


                //Icon id display
                IconButton(onClick = { expandCard = !expandCard }) {
                    if (expandCard) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_up_24),
                            contentDescription = "Expand Arrow Up"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                            contentDescription = "Expand Arrow Down"
                        )
                    }
                }
                if (expandCard) {
                    Divider()
                    Text(
                        text = "iconId:", modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp)
                            .align(Alignment.Start)
                    )


                    //Display list of icons in "parts" many columns
                    val parts = 2
                    val size = (iconIdList.size / parts) + (iconIdList.size % 2)

                    var subLists: List<List<Long>> = iconIdList.chunked(size)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        subLists.forEach() {
                            Column {
                                for (id in it) {
                                    Row(modifier = Modifier.padding(top = 2.dp)) {
                                        Text(
                                            text = "$id -->",
                                            modifier = Modifier.padding(end = 5.dp)
                                        )
                                        Icon(
                                            painter = painterResource(id = fromId(id)),
                                            contentDescription = "Icon $id"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

}


@Composable
fun TitleBackPlusSection(
    title: String,
    onAddRoomClick: (String) -> Unit,
    onBackArrowClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(40.dp)
                    .clickable { onBackArrowClick("Default") }
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = title,
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_add_24),
            contentDescription = "Add Room",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(46.dp)
                .clickable { onAddRoomClick("Default") }
        )
    }
}


@Composable
fun DevicesSection(
    devices: List<Device>,
    onAddFavoritesClick: (device: Device) -> Unit,
    onDeleteFavoritesClick: (device: Device) -> Unit,
    onToggleClick: (deviceId: Long) -> Unit,
    onEditClick: (device: Device) -> Unit,
    onDeleteClick: (deviceId: Long) -> Unit
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
            DeviceItem(
                device = devices[device],
                onAddFavoritesClick = onAddFavoritesClick,
                onDeleteFavoritesClick = onDeleteFavoritesClick,
                onToggleClick = onToggleClick,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick,
            )
        }
    }

}

@Composable
fun DeviceItem(
    device: Device,
    onAddFavoritesClick: (device: Device) -> Unit,
    onDeleteFavoritesClick: (device: Device) -> Unit,
    onToggleClick: (deviceId: Long) -> Unit,
    onEditClick: (device: Device) -> Unit,
    onDeleteClick: (deviceId: Long) -> Unit

) {

    //Mutable State to open Drop Down Menu
    var expanded by remember { mutableStateOf(false) }


    //Pick color, depend on device.status
    val color =
        if (device.status) CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer) else CardDefaults.cardColors()
    val stateText = if (device.status) "On" else "Off"



    Card(
        modifier = Modifier
            .height(110.dp)
            .defaultMinSize(160.dp),
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
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                contentDescription = "Edit",
                Modifier
                    .size(36.dp)
                    .align(Alignment.TopEnd)
                    .clickable { expanded = true },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset(50.dp, (-50).dp)
            ) {
                DropdownMenuItem(
                    text = {
                        if (device.isFavourite) {
                            Text(text = "Delete from Favorites")
                        } else {
                            Text(text = "Add to Favorites")
                        }
                    },
                    onClick = {
                        if (device.isFavourite) {
                            onDeleteFavoritesClick(device)
                        } else {
                            onAddFavoritesClick(device)
                        }
                        expanded = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Toggle") },
                    onClick = {
                        onToggleClick(device.deviceId)
                        expanded = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Edit") },
                    onClick = {
                        onEditClick(device)
                        expanded = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Delete") },
                    onClick = {
                        onDeleteClick(device.deviceId)
                        expanded = false
                    })

            }
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
@Preview(showBackground = true)
fun Rooms_DetailPreview() {

    val tempDeviceList =
        listOf(
            Device("device", 1, "Lampki", 1, false, 14, 1, false),
            Device("device", 2, "Lampki2", 2, false, 16, 1, false),
            Device("device", 1, "Lampki", 1, false, 14, 1, false),
            Device("device", 2, "Lampki2", 2, false, 16, 1, false),
            Device("device", 1, "Lampki", 1, false, 14, 2, false),
            Device("device", 2, "Lampki2", 2, false, 16, 2, false)
        )

    Rooms_DetailScreen(devices = tempDeviceList, 1, {}, {}, {}, {}, {})
}
