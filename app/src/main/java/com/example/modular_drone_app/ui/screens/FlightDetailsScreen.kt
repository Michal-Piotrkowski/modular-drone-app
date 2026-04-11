package com.example.modular_drone_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modular_drone_app.ui.components.OsmMapScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightDetailsScreen(
    flightPoints: List<Pair<Double, Double>> = listOf(
        Pair(52.2296756, 21.0122287), // Start
        Pair(52.2305000, 21.0135000),
        Pair(52.2315000, 21.0142000),
        Pair(52.2325000, 21.0130000),
        Pair(52.2335000, 21.0145000),
        Pair(52.2345000, 21.0160000)  // Koniec
    ),
    duration: String = "00:20:04",
    flightClass: String = "Safety check",
    spyMode: String = "Turned on",
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flight Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E1E1E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Map at the top of the screen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFF353535))
            ) {
                OsmMapScreen(flightPoints = flightPoints)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Route Summary", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    DetailRow(label = "Duration", value = duration)
                    DetailRow(label = "Flight Class", value = flightClass)
                    DetailRow(label = "Spy Mode", value = spyMode)
                    DetailRow(label = "Number of Route Points", value = "${flightPoints.size}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Flight points list
            Text(
                text = "Registered Checkpoints",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(flightPoints) { index, point ->
                    PointCard(index = index + 1, lat = point.first, lon = point.second)
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun PointCard(index: Int, lat: Double, lon: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFF007AFF), shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$index", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Latitude: $lat", color = Color.White, fontSize = 14.sp)
                Text("Longitude: $lon", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightDetailsScreenPreview() {
    FlightDetailsScreen()
}
