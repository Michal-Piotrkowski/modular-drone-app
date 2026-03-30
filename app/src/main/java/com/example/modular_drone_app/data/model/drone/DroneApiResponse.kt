package com.example.modular_drone_app.data.model.drone

import com.example.modular_drone_app.data.model.module.ModuleData
import kotlinx.serialization.Serializable

@Serializable
data class DroneApiResponse(
    val type: String,              // np. "DRONE_STATE"
    val batteryLevel: Float,       // np. 85.5
    val droneStatus: String,       // np. "READY", "ARMED"
    val modules: List<ModuleData>  // Prosta lista modułów
)