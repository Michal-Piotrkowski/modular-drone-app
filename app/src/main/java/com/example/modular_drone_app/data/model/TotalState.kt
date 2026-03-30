package com.example.modular_drone_app.data.model

import com.example.modular_drone_app.data.model.drone.DroneData

data class TotalState(
    val drone: DroneData = DroneData(),
    val isAppConnectedToHub: Boolean = false
)