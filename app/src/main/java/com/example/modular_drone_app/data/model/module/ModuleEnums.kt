package com.example.modular_drone_app.data.model.module

enum class ModuleType {
    GPS,
    SENSORS,
    GRIPPER,
    ELECTROMAGNET,
    UNKNOWN
}

enum class ModuleStatus(val label: String, val colorHex: Long) {
    ACTIVE("Aktywny", 0xFF2CCB6C),

    INACTIVE("Nieaktywny", 0xFF9D9D9D),

    ERROR("Błąd krytyczny", 0xFFD01A1A),

    OFFLINE("Rozłączony", 0xFF252525),

    MAINTENANCE("Serwis", 0xFFFF4400),

    UNKNOWN("Nieznany", 0xFF9D9D9D)
}

enum class ElectromagnetState {
    ON,
    OFF
}

enum class GripperState {
    OPEN,
    CLOSED,
    JAMMED,
    OPENING,
    CLOSING
}