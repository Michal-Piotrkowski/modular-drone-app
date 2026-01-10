package com.example.modular_drone_app.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.modular_drone_app.R
import com.example.modular_drone_app.ui.theme.AppBackground
import com.example.modular_drone_app.ui.theme.PrimaryAccent
import com.example.modular_drone_app.ui.theme.StatusConnected
import com.example.modular_drone_app.ui.theme.StatusDisconnected

@Composable
fun MainDroneStatusCard() {
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF3E2723),
                            AppBackground
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                WeatherInfoSection()

                Box(
                    modifier = Modifier
                        .weight(1f)
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

                BottomStatusSection()
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
private fun BottomStatusSection() {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            StatusRow(label = "Dron", status = "Połączono", isConnected = true)
            StatusRow(label = "HUB", status = "Połączono", isConnected = true)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(
                onClick = { Toast.makeText(context, "Button start clicked!", Toast.LENGTH_SHORT).show() }
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start",
                    tint = PrimaryAccent,
                    modifier = Modifier.size(48.dp)

                )
                Text("Rozpocznij", color = PrimaryAccent, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun StatusRow(label: String, status: String, isConnected: Boolean) {
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
                    color = if (isConnected) StatusConnected else StatusDisconnected,
                    width = 1.5.dp,
                    shape = RoundedCornerShape(50),
                )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = status, style = MaterialTheme.typography.bodyMedium)
    }
}