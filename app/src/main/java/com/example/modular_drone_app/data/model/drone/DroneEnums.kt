package com.example.modular_drone_app.data.model.drone

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DroneSystemState {
    @SerialName("ACTIVE") ACTIVE,
    @SerialName("IDLE") IDLE,
    @SerialName("ERROR") ERROR,
    @SerialName("OFFLINE") OFFLINE
}