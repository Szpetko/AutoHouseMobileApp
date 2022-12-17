package com.plcoding.weatherapp.domain.weather

import androidx.annotation.DrawableRes
import androidx.compose.runtime.snapshots.SnapshotApplyResult.Success.check
import pl.autohouse.autohousemobileapp.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        weatherDesc = "Sunny",
        iconRes = R.drawable.sunny_icon
    )
    object MainlyClear : WeatherType(
        weatherDesc = "Mainly Sunny",
        iconRes = R.drawable.partly_cloudy_day_48px
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = "Cloudy",
        iconRes = R.drawable.partly_cloudy_day_48px
    )
    object Overcast : WeatherType(
        weatherDesc = "Cloudy",
        iconRes = R.drawable.ic_outline_cloud_24
    )
    object Foggy : WeatherType(
        weatherDesc = "Foggy",
        iconRes = R.drawable.foggy_48px
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = "Foggy",
        iconRes = R.drawable.foggy_48px
    )
    object LightDrizzle : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object SlightRain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object ModerateRain : WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object HeavyRain : WeatherType(
        weatherDesc = "Heavy Rain",
        iconRes = R.drawable.rainy_48px
    )
    object HeavyFreezingRain: WeatherType(
        weatherDesc = "Heavy Rain",
        iconRes = R.drawable.rainy_48px
    )
    object SlightSnowFall: WeatherType(
        weatherDesc = "Snowy",
        iconRes = R.drawable.cloudy_snowing_48px
    )
    object ModerateSnowFall: WeatherType(
        weatherDesc = "Snowy",
        iconRes = R.drawable.weather_snowy_48px
    )
    object HeavySnowFall: WeatherType(
        weatherDesc = "Snowy",
        iconRes = R.drawable.weather_snowy_48px
    )
    object SnowGrains: WeatherType(
        weatherDesc = "Snowy",
        iconRes = R.drawable.weather_snowy_48px
    )
    object SlightRainShowers: WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object ModerateRainShowers: WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object ViolentRainShowers: WeatherType(
        weatherDesc = "Rainy",
        iconRes = R.drawable.rainy_48px
    )
    object SlightSnowShowers: WeatherType(
        weatherDesc = "Snowy",
        iconRes = R.drawable.cloudy_snowing_48px
    )
    object HeavySnowShowers: WeatherType(
        weatherDesc = "Heavy Snow",
        iconRes = R.drawable.cloudy_snowing_48px
    )
    object ModerateThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm",
        iconRes = R.drawable.thunderstorm_48px
    )
    object SlightHailThunderstorm: WeatherType(
        weatherDesc = "Thunderstorm",
        iconRes = R.drawable.thunderstorm_48px
    )
    object HeavyHailThunderstorm: WeatherType(
        weatherDesc = "Heavy Thunderstorm",
        iconRes = R.drawable.thunderstorm_48px
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}