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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swimtrack.data.local.TrainingEntity
import com.example.swimtrack.viewmodel.WeatherUiState

@Composable
fun HomeScreen(
    trainings: List<TrainingEntity>,
    weatherUiState: WeatherUiState,
    onAddTraining: () -> Unit,
    onSettings: () -> Unit,
    onDeleteTraining: (TrainingEntity) -> Unit,
    onRetryWeather: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "SwimTrack",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * TARJETA DEL CLIMA
         */

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Condiciones para entrenar",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                when (weatherUiState) {

                    is WeatherUiState.Loading -> {

                        Row(
                            horizontalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {

                            CircularProgressIndicator()

                            Text(
                                text = "Obteniendo clima..."
                            )
                        }
                    }

                    is WeatherUiState.Success -> {

                        val weather =
                            weatherUiState.weather.current

                        Text(
                            text = "Ubicación: Quito"
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                "Temperatura: ${weather.temperature} °C"
                        )

                        Text(
                            text =
                                "Humedad: ${weather.humidity} %"
                        )

                        Text(
                            text =
                                "Viento: ${weather.windSpeed} km/h"
                        )
                    }

                    is WeatherUiState.Error -> {

                        Text(
                            text = weatherUiState.message
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Button(
                            onClick = onRetryWeather
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * BOTONES PRINCIPALES
         */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
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

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * LISTA DE ENTRENAMIENTOS
         */

        Text(
            text = "Entrenamientos registrados",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        if (trainings.isEmpty()) {

            Text(
                text = "No hay entrenamientos registrados"
            )

        } else {

            LazyColumn(
                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = trainings,
                    key = { it.id }
                ) { training ->

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            Text(
                                text =
                                    "${training.style} - ${training.distance} m",
                                style =
                                    MaterialTheme.typography.titleSmall
                            )

                            Text(
                                text =
                                    "Tiempo: ${training.time}"
                            )

                            Text(
                                text =
                                    "Fecha: ${training.date}"
                            )

                            if (
                                training.observation
                                    .isNotBlank()
                            ) {

                                Text(
                                    text =
                                        "Observación: ${training.observation}"
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            Button(
                                onClick = {
                                    onDeleteTraining(
                                        training
                                    )
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