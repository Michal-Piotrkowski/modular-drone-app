package com.example.modular_drone_app.data.model.module

import kotlinx.serialization.Serializable

@Serializable
data class ModuleData(
    val name: String,       // np. "electromagnet"
    val isActive: Boolean   // np. true / false
)