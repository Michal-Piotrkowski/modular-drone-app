package com.example.modular_drone_app.data.model

import com.example.modular_drone_app.data.model.drone.DroneData
import com.example.modular_drone_app.data.model.module.ModuleData

data class TotalState(
    val drone: DroneData = DroneData(),

    val modules: List<ModuleData> = emptyList(),

    val isAppConnectedToHub: Boolean = false
)