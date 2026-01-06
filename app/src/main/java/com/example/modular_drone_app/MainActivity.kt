package com.example.modular_drone_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.modular_drone_app.ui.screens.home.HomeScreen
import com.example.modular_drone_app.ui.theme.ModulardroneappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModulardroneappTheme {
                HomeScreen()
            }
        }
    }
}