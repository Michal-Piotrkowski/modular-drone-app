package com.example.modular_drone_app.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.modular_drone_app.ui.components.FlightModeCard
import com.example.modular_drone_app.ui.components.LastFlightMapCard
import com.example.modular_drone_app.ui.components.MainDroneStatusCard
import com.example.modular_drone_app.ui.viewmodel.DroneViewModel

@Composable
fun HomeScreen(
    viewModel: DroneViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Rozpocznij lot dronem",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        LastFlightMapCard()

        Spacer(modifier = Modifier.height(24.dp))

        MainDroneStatusCard(viewModel)

        Spacer(modifier = Modifier.height(30.dp))

        FlightModeCard()
    }
}