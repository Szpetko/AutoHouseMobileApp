package pl.autohouse.autohousemobileapp.model

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import pl.autohouse.autohousemobileapp.R

val iconIdList: List<Long> = listOf(1L, 2L, 3L, 4L, 5L, 6L)

fun fromId(iconId: Long): Int {
    return when (iconId) {
        1L -> R.drawable.sunny_icon
        2L -> R.drawable.ic_outline_lightbulb_24
        3L -> R.drawable.ic_outline_wb_incandescent_24
        4L -> R.drawable.ic_outline_wb_iridescent_24
        5L -> R.drawable.ic_outline_tv_24
        6L -> R.drawable.ic_outline_speaker_24
        else -> R.drawable.sunny_icon
    }
}

fun fromIcon(iconRes: Int): Long{
    return when (iconRes) {
        R.drawable.sunny_icon -> 1L
        R.drawable.ic_outline_lightbulb_24 -> 2L
        R.drawable.ic_outline_wb_incandescent_24 -> 3L
        R.drawable.ic_outline_wb_iridescent_24 -> 4L
        R.drawable.ic_outline_tv_24 -> 5L
        R.drawable.ic_outline_speaker_24 -> 6L
        else -> 1L
    }
}