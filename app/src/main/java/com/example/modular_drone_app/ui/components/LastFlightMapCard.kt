package com.example.modular_drone_app.ui.components

import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreVert

import androidx.compose.foundation.background
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
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import com.example.modular_drone_app.ui.theme.MapBlue

@Composable
fun LastFlightMapCard(
    flightPoints: List<Pair<Double, Double>> = listOf(
        Pair(52.2296756, 21.0122287),
        Pair(52.2305000, 21.0135000),
        Pair(52.2315000, 21.0142000),
        Pair(52.2325000, 21.0130000),
        Pair(52.2335000, 21.0145000),
        Pair(52.2345000, 21.0160000)
    ),
    duration: String = "00:20:04",
    flightClass: String = "Safety check",
    spyMode: String = "Turned on",
    onOptionsClick: () -> Unit = {}
) {
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
                OsmMapScreen(
                    flightPoints = flightPoints
                )
            }

            // Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.3f)) // Reduced alpha for better road visibility
            )

            // Content
            Box(modifier = Modifier.padding(16.dp)) {

                // Top Right Menu Icon
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .clickable { onOptionsClick() }
                )

                // Card Title
                Text(
                    text = "Last Flight Data",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                // Route and Stats
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

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
                            value = duration
                        )
                        StatItem(
                            icon = Icons.Default.Warning,
                            label = "Class of flight",
                            value = flightClass
                        )
                        StatItem(
                            icon = Icons.Default.Face,
                            label = "Spy Mode",
                            value = spyMode
                        )
                    }
                }
            }
        }
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
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}


@Composable
fun OsmMapScreen(
    flightPoints: List<Pair<Double, Double>>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
                controller.setZoom(15.0)
            }
        },
        update = { view ->
            if (flightPoints.isEmpty()) return@AndroidView

            val geoPoints = flightPoints.map { GeoPoint(it.first, it.second) }
            val startPoint = geoPoints.first()
            val endPoint = geoPoints.last()

            view.controller.setCenter(startPoint)

            view.overlays.clear()

            val startMarker = Marker(view).apply {
                position = startPoint
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                title = "Start"
                // Highlight start marker
                icon.setTint(android.graphics.Color.GREEN)
            }

            val endMarker = Marker(view).apply {
                position = endPoint
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                title = "End"
                // Highlight end marker in pink
                icon.setTint(android.graphics.Color.MAGENTA)
            }

            val route = Polyline().apply {
                setPoints(geoPoints)
                outlinePaint.color = android.graphics.Color.BLUE
                outlinePaint.strokeWidth = 8f
            }

            view.overlays.add(route)
            view.overlays.add(startMarker)
            if (geoPoints.size > 1) {
                view.overlays.add(endMarker)
            }

            // Automatically show info windows for Start and End
            startMarker.showInfoWindow()
            if (geoPoints.size > 1) {
                endMarker.showInfoWindow()
            }

            view.invalidate()
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun LastFlightMapCardPreview() {
    MaterialTheme {
        LastFlightMapCard()
    }
}
