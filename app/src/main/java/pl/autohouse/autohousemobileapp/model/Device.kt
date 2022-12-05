package pl.autohouse.autohousemobileapp.model


class Device(
    val type: String,
    val deviceId: Long,
    val name: String,
    val iconId: Long,
    var status: Boolean,
    val pinAddress: Int,
    val roomId: Long
)
