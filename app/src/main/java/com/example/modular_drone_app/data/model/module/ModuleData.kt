package com.example.modular_drone_app.data.model.module

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("type")
sealed interface ModuleData {
    val id: String
    val status: ModuleStatus
    val swVersion: String
}


@Serializable
@SerialName("ELECTROMAGNET")
data class ElectromagnetModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,

    val electromagnetState: ElectromagnetState
) : ModuleData

@Serializable
@SerialName("GRIPPER")
data class GripperModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,

    val gripperState: GripperState
) : ModuleData

@Serializable
@SerialName("GPS")
data class GPSModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,

    val latitude: Double,
    val longitude: Double,
    val altitude: Double
) : ModuleData

@Serializable
@SerialName("SENSORS")
data class SensorsModuleData(
    override val id: String,
    override val status: ModuleStatus,
    override val swVersion: String,

    val temperature: Double,
    val humidity: Double,
    val pressure: Double
) : ModuleData