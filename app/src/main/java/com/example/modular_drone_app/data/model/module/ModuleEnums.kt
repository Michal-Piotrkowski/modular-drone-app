package com.example.modular_drone_app.data.model.module

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ModuleType {
    GPS,
    SENSORS,
    GRIPPER,
    ELECTROMAGNET,
    UNKNOWN
}

@Serializable
enum class ModuleStatus(val label: String, val colorHex: Long) {
    @SerialName("ACTIVE")
    ACTIVE("Aktywny", 0xFF2CCB6C),

    @SerialName("ON")
    ON("Włączony", 0xFF2CCB6C),

    @SerialName("IDLE")
    IDLE("Czuwanie", 0xFFFFC107),

    @SerialName("OFF")
    OFF("Wyłączony", 0xFF9D9D9D),

    @SerialName("INACTIVE")
    INACTIVE("Nieaktywny", 0xFF9D9D9D),

    @SerialName("ERROR")
    ERROR("Błąd krytyczny", 0xFFD01A1A),

    @SerialName("OFFLINE")
    OFFLINE("Rozłączony", 0xFF252525),

    @SerialName("MAINTENANCE")
    MAINTENANCE("Serwis", 0xFFFF4400),

    @SerialName("UNKNOWN")
    UNKNOWN("Nieznany", 0xFF9D9D9D)
}

@Serializable
enum class ElectromagnetState {
    ON,
    OFF
}

@Serializable
enum class GripperState {
    OPEN,
    CLOSED,
    JAMMED,
    OPENING,
    CLOSING
}