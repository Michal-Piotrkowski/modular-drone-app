package com.example.modular_drone_app.ui.screens.upload

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modular_drone_app.ui.theme.*

@Composable
fun UploadScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Prześlij dane", style = MaterialTheme.typography.headlineMedium)
                Text("Wybrano: 0", color = TextGray, fontSize = 12.sp)
            }

            Button(
                onClick = { /* Send */ },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text("Wyślij", color = TextWhite)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Data List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 100.dp) // Miejsce na dole
        ) {
            item { DateHeader("16.11.2025") }
            items(3) { LogItem() }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                DateHeader("09.11.2025")
            }
            items(2) { LogItem() }
        }
    }
}

@Composable
fun DateHeader(date: String) {
    Text(
        text = date,
        color = TextGray,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun LogItem() {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(SurfaceColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Decoration Box
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(8.dp)
                .background(Color(0xFF333333), RoundedCornerShape(4.dp))
        )

        // Checkbox
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            colors = CheckboxDefaults.colors(
                checkedColor = PrimaryAccent,
                uncheckedColor = TextGray,
                checkmarkColor = TextWhite
            )
        )
    }
}