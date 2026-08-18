package com.example.swimtrack.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onAddTraining: () -> Unit,
    onSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "SwimTrack")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onAddTraining) {
            Text("Registrar entrenamiento")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onSettings) {
            Text("Ajustes")
        }
    }
}