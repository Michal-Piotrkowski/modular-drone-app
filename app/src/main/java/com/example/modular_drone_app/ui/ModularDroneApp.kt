package com.example.modular_drone_app.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.modular_drone_app.ui.components.DroneBottomBar
import com.example.modular_drone_app.ui.navigation.Screen
import com.example.modular_drone_app.ui.screens.config.ConfigScreen // Import
import com.example.modular_drone_app.ui.screens.home.HomeScreen
import com.example.modular_drone_app.ui.screens.update.UpdateScreen // Import
import com.example.modular_drone_app.ui.screens.upload.UploadScreen // Import
import com.example.modular_drone_app.ui.screens.FlightDetailsScreen // Import
import com.example.modular_drone_app.ui.theme.AppBackground
import com.example.modular_drone_app.ui.viewmodel.DroneViewModel

@Composable
fun ModularDroneApp() {
    val navController = rememberNavController()
    val viewModel: DroneViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Config.route,
        Screen.Update.route,
        Screen.Upload.route
    )

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)) + fadeIn(tween(300)),
                exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)) + fadeOut(tween(300))
            ) {
                DroneBottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(400)) + fadeIn(animationSpec = tween(400)) },
            exitTransition = { fadeOut(animationSpec = tween(400)) },
            popEnterTransition = { fadeIn(animationSpec = tween(400)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400)) }
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onMapDetailsClick = {
                        navController.navigate("flight_details")
                    }
                )
            }
            composable("flight_details") {
                FlightDetailsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
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