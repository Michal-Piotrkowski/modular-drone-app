package com.example.modular_drone_app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Color.kt
 *
 * Defines the color palette and semantic color tokens used throughout the Modular Drone App.
 * This file is organized into two distinct sections:
 * 1. Color Palette: Raw HEX definitions for brand, neutral, and functional colors.
 * 2. Semantics: Context-aware definitions that map raw colors to specific UI uses
 * (e.g., backgrounds, status indicators, map elements).
 *
 * The scheme is designed primarily for a dark-mode interface to ensure high contrast
 * and readability during outdoor drone operations.
 */

// Primary Colors
val BaseBlack = Color(0xFF121212)
val DarkGray = Color(0xFF252525)
val BaseOrange = Color(0xFFFF4400)

// Accent Colors
val BaseGreen = Color(0xFF2CCB6C)
val BaseRed = Color(0xFFD01A1A)
val MapBlue = Color(0xFF00B4D8)
val MapPink = Color(0xFFE3009F)

// Text
val TextWhite = Color(0xFFEEEEEE)
val TextGray = Color(0xFF9D9D9D)

//================================
//=========== Semantics ==========
//================================

// Backgrounds and Surfaces
val AppBackground = BaseBlack
val SurfaceColor = DarkGray
val SurfaceTransparent = Color(0xFF636363).copy(alpha = 0.15f)

// Accents and Actions
val PrimaryAccent = BaseOrange
val ActionSuccess = BaseGreen

// Status Indicators
val StatusConnected = BaseGreen
val StatusDisconnected = BaseRed

val StatusErrorBg = BaseRed.copy(alpha = 0.5f)

// Map Elements
val MapIconTint = MapBlue
val MapFinishPoint = MapPink
val MapRouteLine = BaseOrange