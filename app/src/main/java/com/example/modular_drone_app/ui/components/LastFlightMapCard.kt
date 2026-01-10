package com.example.modular_drone_app.ui.components

import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modular_drone_app.ui.theme.BaseGreen
import com.example.modular_drone_app.ui.theme.MapBlue
import com.example.modular_drone_app.ui.theme.MapPink
import com.example.modular_drone_app.ui.theme.MapRouteLine
import com.example.modular_drone_app.ui.theme.TextGray
import com.example.modular_drone_app.ui.theme.TextWhite

@Composable
fun LastFlightMapCard() {
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF353535))
            ) {
                // TODO: PUT MAP IMAGE HERE
            }

            // Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            // Content
            Box(modifier = Modifier.padding(16.dp)) {

                // Top Right Menu Icon
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = TextWhite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                )

                // Card Title
                Text(
                    text = "Dane z ostatniego lotu",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    color = TextWhite,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                // Route and Stats
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Route Canvas with Labels
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                    ) {
                        RouteCanvas(modifier = Modifier.fillMaxSize())

                        RouteLabel(
                            text = "Start",
                            isStart = true,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .offset(x = 10.dp, y = (-20).dp)
                        )

                        RouteLabel(
                            text = "Finish",
                            isStart = false,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .offset(x = (-20).dp, y = (-10).dp)
                        )
                    }

                    // Stats Column
                    Column(
                        modifier = Modifier
                            .width(140.dp)
                            .padding(start = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatItem(
                            icon = Icons.Default.DateRange,
                            label = "Duration",
                            value = "00:20:04"
                        )
                        StatItem(
                            icon = Icons.Default.Warning,
                            label = "Class of flight",
                            value = "Safety check"
                        )
                        StatItem(
                            icon = Icons.Default.Face,
                            label = "Spy Mode",
                            value = "Turned on"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun RouteCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val startX = 20.dp.toPx()
        val startY = h * 0.7f

        val endX = w - 30.dp.toPx()
        val endY = h * 0.6f


        val mid1X = w * 0.4f
        val mid1Y = h * 0.3f

        val mid2X = w * 0.5f
        val mid2Y = h * 0.8f


        val path = Path().apply {
            moveTo(startX, startY)
            lineTo(mid1X, mid1Y)
            lineTo(mid2X, mid2Y)
            lineTo(endX, endY)
        }

        drawPath(
            path = path,
            color = MapRouteLine,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        drawCircle(
            color = BaseGreen,
            radius = 6.dp.toPx(),
            center = Offset(startX, startY)
        )

        drawCircle(
            color = MapPink,
            radius = 6.dp.toPx(),
            center = Offset(endX, endY)
        )
    }
}

@Composable
fun RouteLabel(text: String, isStart: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, color = TextWhite, fontSize = 12.sp)
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = TextWhite,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun StatItem(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MapBlue,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = TextWhite,
                fontSize = 12.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = TextGray,
                fontSize = 11.sp
            )
        }
    }
}