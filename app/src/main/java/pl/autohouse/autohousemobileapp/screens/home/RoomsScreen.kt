package pl.autohouse.autohousemobileapp.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.autohouse.autohousemobileapp.R
import pl.autohouse.autohousemobileapp.model.Device
import pl.autohouse.autohousemobileapp.model.Room
import pl.autohouse.autohousemobileapp.navigation.Screen
import pl.autohouse.autohousemobileapp.screens.home.details.EditDialogItem

@Composable
fun RoomsScreen(
    rooms: List<Room>?,
    onRoomCardClick: (roomId: Long) -> Unit,
    onAddRoomClick: (room: Room) -> Unit,
    onChangeRoomClick: (room: Room) -> Unit,
    onDeleteClick: (roomId: Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
    ) {

        //Mutable States to open Dialogs
        var openAddDialog = remember {
            mutableStateOf(false)
        }
        var openEditDialog = remember {
            mutableStateOf(false)
        }

        //Mutable State to enable click on Add Room Button
        var enableAddRoom = remember {
            mutableStateOf(true)
        }

        //Temp mutable Room for Edit Room Dialog
        var room by remember {
            mutableStateOf(Room("None", 0, "None", 0))
        }


        //Title with Add Button
        TitlePlusSection(
            title = "Room", onAddRoomClick = {
                openAddDialog.value = true
            },
            enableAddRoom = enableAddRoom
        )

        //Null Check to display Rooms
        if (rooms != null && rooms.isNotEmpty()) {
            RoomCardsSection(
                rooms,
                onRoomCardClick = onRoomCardClick,
                onEditClick = {
                    room = it
                    openEditDialog.value = true
                },
                onDeleteClick = {
                    onDeleteClick(it)
                }
            )

        } else {
            enableAddRoom.value = false
            Text(
                text = "Failed to load data from the server",
                modifier = Modifier
                    .padding(40.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        //Open add Room dialog
        if (openAddDialog.value) {
            AddRoomDialogItem(
                onCancelClick = { openAddDialog.value = false },
                onAddClick = {
                    openAddDialog.value = false
                    onAddRoomClick(it)
                }
            )
        }

        //Open edit Room dialog
        if (openEditDialog.value) {
            EditRoomDialogItem(
                room = room,
                onCancelClick = {
                    openEditDialog.value = false
                },
                onChangeRoomClick = {
                    openEditDialog.value = false
                    onChangeRoomClick(it)
                }
            )
        }

    }
}

@Composable
fun TitlePlusSection(
    title: String,
    onAddRoomClick: (String) -> Unit,
    enableAddRoom: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = MaterialTheme.typography.displaySmall.fontSize,
            fontWeight = FontWeight.Bold
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_add_24),
            contentDescription = "Add Room",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(46.dp)
                .clickable(
                    enabled = enableAddRoom.value
                ) { onAddRoomClick("Default") }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomDialogItem(
    onCancelClick: (String) -> Unit,
    onAddClick: (room: Room) -> Unit
) {

    Dialog(
        onDismissRequest = { onCancelClick("Default") }
    ) {

        var newRoomName by remember {
            mutableStateOf("")
        }
        var newRoomIconId by remember {
            mutableStateOf("0")
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
                    value = newRoomName,
                    onValueChange = { newRoomName = it },
                    label = { Text(text = "Room name:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                TextField(
                    value = newRoomIconId,
                    onValueChange = { newRoomIconId = it },
                    label = { Text(text = "iconId:") },
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


                    if (newRoomName != "" && newRoomIconId != "") {
                        filled = true
                    }

                    Button(
                        onClick = {
                            var newRoom = Room("room", 0, newRoomName, newRoomIconId.toLong())
                            onAddClick(newRoom)
                        },
                        enabled = filled
                    ) {
                        Text(text = "Add")
                    }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRoomDialogItem(
    room: Room,
    onCancelClick: (String) -> Unit,
    onChangeRoomClick: (room: Room) -> Unit
) {

    Dialog(
        onDismissRequest = { onCancelClick("Default") }
    ) {


        var newRoomName by remember {
            mutableStateOf(room.name)
        }
        var newRoomIconId by remember {
            mutableStateOf(room.iconId.toString())
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
                    value = room.roomId.toString(),
                    onValueChange = { newRoomIconId = it },
                    label = { Text(text = "roomId:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    enabled = false
                )
                TextField(
                    value = newRoomName,
                    onValueChange = { newRoomName = it },
                    label = { Text(text = "Room name:") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
                TextField(
                    value = newRoomIconId,
                    onValueChange = { newRoomIconId = it },
                    label = { Text(text = "iconId:") },
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


                    if (newRoomName != "" && newRoomIconId != "") {
                        filled = true
                    }

                    Button(
                        onClick = {
                            var newRoom = Room(room.type, room.roomId, newRoomName, room.iconId)
                            onChangeRoomClick(newRoom)
                        },
                        enabled = filled
                    ) {
                        Text(text = "Change")
                    }
                }
            }

        }
    }

}


@Composable
fun RoomCardsSection(
    rooms: List<Room>,
    onRoomCardClick: (roomId: Long) -> Unit,
    onEditClick: (room: Room) -> Unit,
    onDeleteClick: (roomId: Long) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 90.dp),
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        items(rooms.size) { room ->
            RoomItemCard(
                room = rooms[room],
                onClick = onRoomCardClick,
                onEditClick = onEditClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun RoomItemCard(
    room: Room,
    onClick: (roomId: Long) -> Unit,
    onEditClick: (room: Room) -> Unit,
    onDeleteClick: (roomId: Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick(room.roomId) }
    ) {
        Box(
            Modifier
                .padding(vertical = 8.dp, horizontal = 15.dp)
                .fillMaxSize()
        ) {
            Text(
                text = room.name,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = FontWeight.Bold
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
                offset = DpOffset(220.dp, (-50).dp),
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Edit") },
                    onClick = {
                        onEditClick(room)
                        expanded = false
                    })
                DropdownMenuItem(
                    text = { Text(text = "Delete") },
                    onClick = {
                        onDeleteClick(room.roomId)
                        expanded = false
                    })

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun RoomsScreenPreview() {

    val rooms = listOf(
        Room("room", 1, "Living Room", 1),
        Room("room", 2, "Kitchen", 1),
        Room("room", 3, "Some Room", 1),
        Room("room", 4, "Bed Room", 1),

        )

    RoomsScreen(rooms, {}, {}, {}, {})
}