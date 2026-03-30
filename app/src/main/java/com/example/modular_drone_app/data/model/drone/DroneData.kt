package com.example.modular_drone_app.data.model.drone

import com.example.modular_drone_app.data.model.module.ModuleData
import kotlinx.serialization.Serializable

@Serializable
data class DroneData (
    val id: String = "UNKNOWN",
    val droneStatus: DroneSystemState = DroneSystemState.OFFLINE,
    val batteryLevel: Int = 0,
    val isHubConnected: Boolean = false,
    val modules: List<ModuleData> = emptyList()
)

@Serializable
data class ModuleResponse(
    val name: String,
    val isActive: Boolean
)