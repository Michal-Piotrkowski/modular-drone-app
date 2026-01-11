package com.example.modular_drone_app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modular_drone_app.ui.components.DroneBottomBar
import com.example.modular_drone_app.ui.navigation.Screen
import com.example.modular_drone_app.ui.screens.config.ConfigScreen // Import
import com.example.modular_drone_app.ui.screens.home.HomeScreen
import com.example.modular_drone_app.ui.screens.update.UpdateScreen // Import
import com.example.modular_drone_app.ui.screens.upload.UploadScreen // Import
import com.example.modular_drone_app.ui.theme.AppBackground

@Composable
fun ModularDroneApp() {
    val navController = rememberNavController()

    Scaffold(
        containerColor = AppBackground,
        bottomBar = { DroneBottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Config.route) {
                ConfigScreen()
            }
            composable(Screen.Update.route) {
                UpdateScreen()
            }
            composable(Screen.Upload.route) {
                UploadScreen()
            }
        }
    }
}