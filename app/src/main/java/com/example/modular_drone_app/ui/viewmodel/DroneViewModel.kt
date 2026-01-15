package com.example.modular_drone_app.ui.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modular_drone_app.data.connection.DataNetworkService
import com.example.modular_drone_app.utils.NetworkUtils
import kotlinx.coroutines.launch

class DroneViewModel: ViewModel() {
    val uiState = DataNetworkService.uiState

    fun toggleConnection(context: Context) {
        println("DEBUG: Kliknięto przycisk! Sprawdzam stan...")

        val currentState = uiState.value.drone

        if (currentState.isConnected) {
            println("DEBUG: Jestem połączony, więc rozłączam.")
            DataNetworkService.disconnect()
        } else {
            println("DEBUG: Jestem rozłączony, próbuję łączyć.")

             if (!NetworkUtils.isWifiConnected(context)) {
                 println("DEBUG: Blokada - brak Wi-Fi!")
                 Toast.makeText(context, "Brak Wi-Fi!", Toast.LENGTH_SHORT).show()
                 return
             }

            viewModelScope.launch {
                println("DEBUG: Uruchamiam DataNetworkService.connect...")
                DataNetworkService.connect("10.0.2.2", 8080)
            }
        }
    }
}