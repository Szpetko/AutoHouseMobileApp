package pl.autohouse.autohousemobileapp.model


class Device(
    val type: String,
    var deviceId: Long,
    var name: String,
    var iconId: Long,
    var status: Boolean,
    var pinAddress: Int,
    var roomId: Long,
    var isFavourite: Boolean
)
