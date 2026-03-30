package com.example.modular_drone_app.data.connection

import com.example.modular_drone_app.data.model.TotalState
import com.example.modular_drone_app.data.model.drone.DroneApiResponse
import com.example.modular_drone_app.data.model.drone.DroneSystemState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object DataNetworkService {
    private val socketClient = SocketClient()
    private val _uiState = MutableStateFlow(TotalState())
    val uiState: StateFlow<TotalState> = _uiState.asStateFlow()

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // 👇 PROSTY PARSER. Żadnych udziwnień, żadnego polimorfizmu.
    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    fun connect(ip: String, port: Int) {
        serviceScope.launch {
            println("DEBUG_NET: [Service] Łączenie do $ip:$port")
            val success = socketClient.connect(ip, port)

            if (success) {
                println("DEBUG_NET: [Service] Połączono!")
                updateConnectionStatus(true)
                startListening()
            } else {
                println("DEBUG_NET: [Service] Błąd połączenia.")
                updateConnectionStatus(false)
            }
        }
    }

    fun disconnect() {
        serviceScope.launch {
            socketClient.disconnect()
            updateConnectionStatus(false)
        }
    }

    private suspend fun startListening() {
        socketClient.observeMessages().collect { message ->
            // println("DEBUG_NET: Odebrano: $message")
            parseLine(message)
        }
        updateConnectionStatus(false)
    }

    private fun parseLine(rawMessage: String) {
        try {
            // Dekodujemy prosty JSON
            val response = jsonParser.decodeFromString<DroneApiResponse>(rawMessage)
            println("DEBUG_NET: ✅ JSON OK! Bateria: ${response.batteryLevel}, Modułów: ${response.modules.size}")
            updateStateFromResponse(response)
        } catch (ex: Exception) {
            println("DEBUG_NET: ⚠️ Błąd parsowania: ${ex.message}")
            ex.printStackTrace()
        }
    }

    private fun updateStateFromResponse(response: DroneApiResponse) {
        _uiState.update { current ->
            // Konwersja Stringa "READY" na Enum DroneSystemState
            val mappedStatus = try {
                DroneSystemState.valueOf(response.droneStatus)
            } catch (e: Exception) {
                DroneSystemState.IDLE // Domyślny, jeśli przyjdzie coś dziwnego
            }

            current.copy(
                drone = current.drone.copy(
                    batteryLevel = response.batteryLevel.toInt(), // Rzutujemy float na int dla UI
                    droneStatus = mappedStatus,
                    modules = response.modules,
                    isHubConnected = true
                )
            )
        }
    }

    private fun updateConnectionStatus(isConnected: Boolean) {
        _uiState.update { current ->
            current.copy(
                isAppConnectedToHub = isConnected,
                drone = current.drone.copy(
                    droneStatus = if (isConnected) current.drone.droneStatus else DroneSystemState.OFFLINE
                )
            )
        }
    }

    fun toggleModule(moduleName: String, isCurrentlyActive: Boolean) {
        // Tu logika wysyłania komendy - bez zmian, bo format komend się nie zmienił
        val newValue = if (isCurrentlyActive) "OFF" else "ON"
        val commandJson = """{"type":"SET_MODULE","module":"$moduleName","value":"$newValue"}"""

        serviceScope.launch {
            try {
                socketClient.sendMessage(commandJson)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}