package com.example.modular_drone_app.ui.screens.config

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modular_drone_app.ui.theme.*

@Composable
fun ConfigScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Konfiguruj urządzenia", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // HW Section
        ConfigSectionTitle("Konfiguracja hardware")
        ConfigItem(icon = Icons.Default.Settings, title = "Konfiguracja drona")
        ConfigItem(icon = Icons.Default.Home, title = "Konfiguracja huba")

        Spacer(modifier = Modifier.height(16.dp))

        // Modules Section
        ConfigSectionTitle("Konfiguracja modułów")
        ConfigItem(icon = Icons.Default.AddCircle, title = "Konfiguruj dostępne moduły")

        Spacer(modifier = Modifier.height(16.dp))

        // Sensors Section
        ConfigSectionTitle("Zaawansowane")
        ConfigItem(icon = Icons.Default.Settings, title = "Diagnostyka sensorów")

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun ConfigSectionTitle(title: String) {
    Text(
        text = title,
        color = TextGray,
        fontSize = 12.sp,
        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
    )
}

@Composable
fun ConfigItem(icon: ImageVector, title: String, onClick: () -> Unit = {}) {
    Surface(
        color = SurfaceColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = PrimaryAccent)
            Spacer(modifier = Modifier.width(16.dp))
            Text(title, color = TextWhite, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = TextGray
            )
        }
    }
}