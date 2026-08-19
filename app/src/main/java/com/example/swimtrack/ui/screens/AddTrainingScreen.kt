package com.example.swimtrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swimtrack.data.local.TrainingEntity

@Composable
fun AddTrainingScreen(
    onSaveTraining: (TrainingEntity) -> Unit,
    onBack: () -> Unit
) {
    var style by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var observation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Registrar entrenamiento")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = style,
            onValueChange = { style = it },
            label = { Text("Estilo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = distance,
            onValueChange = { distance = it },
            label = { Text("Distancia en metros") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Tiempo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = observation,
            onValueChange = { observation = it },
            label = { Text("Observación") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val distanceNumber = distance.toIntOrNull()

                if (
                    style.isNotBlank() &&
                    distanceNumber != null &&
                    time.isNotBlank() &&
                    date.isNotBlank()
                ) {
                    val training = TrainingEntity(
                        style = style,
                        distance = distanceNumber,
                        time = time,
                        date = date,
                        observation = observation
                    )

                    onSaveTraining(training)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar entrenamiento")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}