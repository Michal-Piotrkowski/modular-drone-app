package com.example.modular_drone_app.ui.components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modular_drone_app.R
import com.example.modular_drone_app.ui.theme.AppBackground
import com.example.modular_drone_app.ui.theme.PrimaryAccent
import com.example.modular_drone_app.ui.theme.StatusConnected
import com.example.modular_drone_app.ui.theme.StatusDisconnected
import com.example.modular_drone_app.ui.viewmodel.DroneViewModel

@Composable
fun MainDroneStatusCard(
    viewModel: DroneViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val isConnected = uiState.drone.isConnected

    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF3E2723), AppBackground)
                    )
                )
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                WeatherInfoSection()

                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.drone),
                        contentDescription = "Drone Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }


                BottomStatusSection(
                    isConnected = isConnected,
                    droneStatusText = uiState.drone.droneStatus.name,
                    onToggleClick = { context ->
                        viewModel.toggleConnection(context)
                    }
                )
            }
        }
    }
}

@Composable
private fun WeatherInfoSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Text("Dane pogodowe", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text("☀ 26°C", style = MaterialTheme.typography.bodyLarge)
        Text("💨 0 km/h NW", style = MaterialTheme.typography.bodyMedium)
        Text("☂ 0 mm", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun BottomStatusSection(
    isConnected: Boolean,
    droneStatusText: String,
    onToggleClick: (Context) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusRow(
                label = "Dron",
                status = if (isConnected) droneStatusText else "Rozłączono",
                isOk = isConnected
            )
            StatusRow(
                label = "HUB",
                status = if (isConnected) "Połączono" else "Szukanie...",
                isOk = isConnected
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom
        ) {
            OutlinedButton(
                onClick = { onToggleClick(context) },
                border = BorderStroke(
                    1.dp,
                    if (isConnected) Color.Red else PrimaryAccent
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isConnected) Color.Red.copy(alpha = 0.1f) else Color.Transparent,
                    contentColor = if (isConnected) Color.Red else PrimaryAccent
                ),
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (isConnected) Icons.Default.Close else Icons.Default.PlayArrow,
                    contentDescription = if (isConnected) "Stop" else "Start",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = if (isConnected) "ROZŁĄCZ" else "ROZPOCZNIJ",
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Composable
private fun StatusRow(label: String, status: String, isOk: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(50.dp)
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .border(

                    color = if (isOk) StatusConnected else StatusDisconnected,
                    width = 1.5.dp,
                    shape = RoundedCornerShape(50),
                )
                .background(

                    color = if (isOk) StatusConnected else Color.Transparent,
                    shape = RoundedCornerShape(50)
                )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = status, style = MaterialTheme.typography.bodyMedium)
    }
}