package com.example.modular_drone_app.data.model.drone

import com.example.modular_drone_app.data.model.module.ModuleData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DroneApiResponse(
    @SerialName("batteryLevel") val batteryLevel: Int,
    @SerialName("droneStatus") val droneStatus: DroneSystemState,
    @SerialName("modules") val modules: List<ModuleData>
)