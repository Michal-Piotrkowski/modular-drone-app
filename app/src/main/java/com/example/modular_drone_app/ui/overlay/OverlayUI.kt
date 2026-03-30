package com.example.modular_drone_app.ui.overlay

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modular_drone_app.data.connection.DataNetworkService
import com.example.modular_drone_app.data.model.module.ModuleStatus

val HudBackground = Color(0xD9121212)
val HudBorder = Color(0xFF333333)
val HudActive = Color(0xFF00E676)
val HudInactive = Color(0xFF424242)
val HudWarning = Color(0xFFFF5252)

@Composable
fun OverlayControlPanel(
    onClose: () -> Unit,
    onMove: (Int, Int) -> Unit
) {
    val uiState by DataNetworkService.uiState.collectAsState()
    val drone = uiState.drone

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = HudBackground),
        border = androidx.compose.foundation.BorderStroke(1.dp, HudBorder),
        modifier = Modifier
            .width(180.dp)
            .wrapContentHeight()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onMove(dragAmount.x.toInt(), dragAmount.y.toInt())
                }
            }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (drone.isHubConnected) HudActive else HudWarning)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SYSTEM",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("BATTERY", color = Color.Gray, fontSize = 9.sp)
                    Text("${drone.batteryLevel}%", color = if(drone.batteryLevel < 20) HudWarning else HudActive, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { drone.batteryLevel / 100f },
                    modifier = Modifier.fillMaxWidth().height(4.dp).clip(RoundedCornerShape(2.dp)),
                    color = if(drone.batteryLevel < 20) HudWarning else HudActive,
                    trackColor = HudInactive,
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = HudBorder)

            Text("MODULES (${drone.modules.size})", color = Color.Gray, fontSize = 9.sp, modifier = Modifier.align(Alignment.Start))
            Spacer(modifier = Modifier.height(8.dp))

            val displayModules = drone.modules.map { module ->
                ModuleUiItem(
                    name = module.name,
                    isActive = module.isActive
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                displayModules.forEach { mod ->
                    ModuleControlTile(
                        name = mod.name,
                        isActive = mod.isActive,
                        icon = if (mod.name.contains("Magnes", true) || mod.name.contains("electromagnet", true)) Icons.Default.Bolt else Icons.Default.PowerSettingsNew,
                        onClick = {
                            DataNetworkService.toggleModule(mod.name, mod.isActive)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ModuleControlTile(
    name: String,
    isActive: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val backgroundColor = if (isActive) HudActive.copy(alpha = 0.2f) else HudInactive.copy(alpha = 0.3f)
    val contentColor = if (isActive) HudActive else Color.Gray

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name.uppercase(),
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = if (isActive) "ON" else "OFF",
            color = contentColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

data class ModuleUiItem(val name: String, val isActive: Boolean)