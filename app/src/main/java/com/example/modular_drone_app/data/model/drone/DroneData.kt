package com.example.modular_drone_app.data.model.drone

data class DroneData (
    val id: String = "UNKNOWN",
    val droneStatus: DroneSystemState = DroneSystemState.OFFLINE,
    val batteryLevel: Int = 0,
)