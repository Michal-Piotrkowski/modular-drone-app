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
        println("DEBUG_NET: [ViewModel] Kliknięto przycisk Połącz/Rozłącz.")

        val currentState = uiState.value.drone

        if (currentState.isHubConnected) {
            println("DEBUG_NET: [ViewModel] Aktualnie połączony -> Rozłączam.")
            DataNetworkService.disconnect()
        } else {
            println("DEBUG_NET: [ViewModel] Aktualnie rozłączony -> Łączę...")

            // Adres IP - wybierz właściwy!
            // val targetIp = "192.168.1.36" // Dla prawdziwego telefonu
            val targetIp = "10.0.0.2"     // Dla Emulatora
            val targetPort = 8080

            viewModelScope.launch {
                println("DEBUG_NET: [ViewModel] Wywołuję connect($targetIp, $targetPort)")
                DataNetworkService.connect(targetIp, targetPort)
            }
        }
    }
}