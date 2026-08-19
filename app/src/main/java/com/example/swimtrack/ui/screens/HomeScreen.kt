package com.example.swimtrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swimtrack.data.local.TrainingEntity

@Composable
fun HomeScreen(
    trainings: List<TrainingEntity>,
    onAddTraining: () -> Unit,
    onSettings: () -> Unit,
    onDeleteTraining: (TrainingEntity) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(text = "SwimTrack")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = onAddTraining,
                modifier = Modifier.weight(1f)
            ) {
                Text("Registrar")
            }

            Button(
                onClick = onSettings,
                modifier = Modifier.weight(1f)
            ) {
                Text("Ajustes")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (trainings.isEmpty()) {

            Text("No hay entrenamientos registrados")

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = trainings,
                    key = { it.id }
                ) { training ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = "${training.style} - ${training.distance} m"
                            )

                            Text(
                                text = "Tiempo: ${training.time}"
                            )

                            Text(
                                text = "Fecha: ${training.date}"
                            )

                            if (training.observation.isNotBlank()) {
                                Text(
                                    text = "Observación: ${training.observation}"
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    onDeleteTraining(training)
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}