package com.example.modular_drone_app.data.model

sealed interface ModuleData {
    val id: String
    val moduleType: ModuleType
    val status: ModuleStatus
    val swVersion: String
}

data class ElectromagnetModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,
    override val moduleType: ModuleType = ModuleType.ELECTROMAGNET,

    val electromagnetState: ElectromagnetState
) : ModuleData

data class GripperModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,
    override val moduleType: ModuleType = ModuleType.GRIPPER,

    val gripperState: GripperState
) : ModuleData

data class GPSModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,
    override val moduleType: ModuleType = ModuleType.GPS,

    val latitude: Double,
    val longitude: Double,
    val altitude: Double
) : ModuleData

data class SensorsModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,
    override val moduleType: ModuleType = ModuleType.SENSORS,

    val temperature: Double,
    val humidity: Double,
    val pressure: Double
) : ModuleData