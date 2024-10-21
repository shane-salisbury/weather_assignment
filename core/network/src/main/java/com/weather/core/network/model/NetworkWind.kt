package com.weather.core.network.model

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class NetworkWind(
    @SerialName("speed")
    val speed: Double,
    @SerialName("deg")
    val deg: Int,
)