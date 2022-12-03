package pl.autohouse.autohousemobileapp.classes


data class Device(
    val id: Long,
    val name: String,
    val iconId: Long,
    val status: Boolean,
    val pinAddress: Int,
    val roomId: Long
)