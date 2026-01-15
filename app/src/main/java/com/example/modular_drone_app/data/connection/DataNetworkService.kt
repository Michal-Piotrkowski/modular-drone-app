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

    private val serviceScope = CoroutineScope(
        Dispatchers.IO +
        SupervisorJob()
    )

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        classDiscriminator = "type"
    }

    fun connect(ip: String, port: Int) {
        serviceScope.launch {
            val success = socketClient.connect(ip, port)

            if (success) {
                updateConnectionStatus(true)
                startListening()
            } else {
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

    suspend fun startListening() {
        socketClient.observeMessages().collect { message ->
            parseLine(message)
        }
        updateConnectionStatus(false)
    }

    suspend fun parseLine(rawMessage: String){
        try {
            val response = jsonParser.decodeFromString<DroneApiResponse>(rawMessage)
            updateStateFromResponse(response)
        } catch (ex: Exception){
            println("Parsing error: ${ex.message}")
        }
    }

    private fun updateStateFromResponse(response: DroneApiResponse) {
        _uiState.update { current ->
            current.copy(
                drone = current.drone.copy(
                    batteryLevel = response.batteryLevel,
                    droneStatus = response.droneStatus
                ),
                modules = response.modules
            )
        }
    }

    private fun updateConnectionStatus(isConnected: Boolean) {
        _uiState.update { current ->
            current.copy(
                drone = current.drone.copy(
                    isConnected = isConnected,

                    droneStatus = if (isConnected) current.drone.droneStatus else DroneSystemState.OFFLINE
                )
            )
        }
    }

}