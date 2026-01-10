package com.example.modular_drone_app.ui.navigation

import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Lot", Icons.Default.PlayArrow)
    object Config : Screen("config", "Konfiguracja", Icons.Default.Settings)
    object Update : Screen("update", "Aktualizacja", Icons.Default.Refresh)
    object Upload : Screen("upload", "Wyślij dane", Icons.Default.Share)
}