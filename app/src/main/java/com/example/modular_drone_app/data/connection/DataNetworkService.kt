package com.example.modular_drone_app.data.connection

import com.example.modular_drone_app.data.model.TotalState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


object DataNetworkService {
    private val socketClient = SocketClient()

    private val _uiState = MutableStateFlow(TotalState())

    val uiState: StateFlow<TotalState> = _uiState.asStateFlow()


}