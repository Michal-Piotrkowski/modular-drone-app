package com.example.modular_drone_app.ui.screens.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modular_drone_app.ui.theme.*

@Composable
fun UpdateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Aktualizacje", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Sprawdź czy twoje urządzenia są zaktualizowane",
            color = TextGray,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Update Button
        Button(
            onClick = { /* Start update */ },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = StatusDisconnected), // BaseRed
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Wymagana aktualizacja", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Kliknij, aby pobrać", color = TextWhite.copy(alpha = 0.8f), fontSize = 12.sp)
                }
                Icon(
                    imageVector = Icons.Default.Share, // Ikona pobierania
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = TextWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text("Lista aktualizacji", style = MaterialTheme.typography.titleMedium, color = TextGray)
        Spacer(modifier = Modifier.height(16.dp))

        UpdateListItem(
            version = "2005.2.1 - HUB",
            description = "Poprawa wydajności zabezpieczeń",
            isCritical = true
        )
    }
}

@Composable
fun UpdateListItem(version: String, description: String, isCritical: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(version, color = TextWhite, fontWeight = FontWeight.Bold)
            Text(description, color = TextGray, fontSize = 12.sp)
        }
        if (isCritical) {
            Icon(Icons.Default.Warning, contentDescription = "Ważne", tint = BaseRed)
        }
    }
}