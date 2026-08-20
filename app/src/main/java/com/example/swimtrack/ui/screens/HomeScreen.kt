package com.example.swimtrack.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.swimtrack.data.local.TrainingEntity
import com.example.swimtrack.viewmodel.LocationUiState
import com.example.swimtrack.viewmodel.WeatherUiState

@Composable
fun HomeScreen(
    trainings: List<TrainingEntity>,
    weatherUiState: WeatherUiState,
    locationUiState: LocationUiState,
    onAddTraining: () -> Unit,
    onSettings: () -> Unit,
    onDeleteTraining: (TrainingEntity) -> Unit,
    onRetryWeather: () -> Unit,
    onRequestLocation: () -> Unit
) {

    val context = LocalContext.current

    var permissionDenied by remember {
        mutableStateOf(false)
    }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val fineGranted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true

            val coarseGranted =
                permissions[
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ] == true

            if (fineGranted || coarseGranted) {
                permissionDenied = false
                onRequestLocation()
            } else {
                permissionDenied = true
            }
        }

    LaunchedEffect(Unit) {

        val fineGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {

            onRequestLocation()

        } else {

            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "SwimTrack",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Control de entrenamientos de natación",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * ESTADO DE UBICACIÓN
         */

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Ubicación",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                if (permissionDenied) {

                    Text(
                        text =
                            "El acceso a la ubicación fue rechazado. " +
                                    "Se necesita para consultar el clima de tu zona."
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Button(
                        onClick = {
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    ) {
                        Text("Conceder permiso")
                    }

                } else {

                    when (locationUiState) {

                        is LocationUiState.Idle -> {

                            Text(
                                text = "Esperando ubicación..."
                            )
                        }

                        is LocationUiState.Loading -> {

                            Row(
                                horizontalArrangement =
                                    Arrangement.spacedBy(12.dp)
                            ) {

                                CircularProgressIndicator()

                                Text(
                                    text = "Obteniendo ubicación..."
                                )
                            }
                        }

                        is LocationUiState.Success -> {

                            Text(
                                text =
                                    "Ubicación obtenida correctamente"
                            )
                        }

                        is LocationUiState.Error -> {

                            Text(
                                text =
                                    locationUiState.message
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Button(
                                onClick = onRequestLocation
                            ) {
                                Text("Reintentar")
                            }
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        /*
         * CLIMA
         */

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Condiciones para entrenar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                when (weatherUiState) {

                    is WeatherUiState.Loading -> {

                        if (locationUiState is LocationUiState.Error) {

                            Text(
                                text =
                                    "Necesitamos una ubicación válida para consultar el clima."
                            )

                        } else {

                            Row(
                                horizontalArrangement =
                                    Arrangement.spacedBy(12.dp)
                            ) {

                                CircularProgressIndicator()

                                Text(
                                    text = "Consultando clima..."
                                )
                            }
                        }
                    }

                    is WeatherUiState.Success -> {

                        val weather =
                            weatherUiState.weather.current

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
                            text =
                                weatherUiState.message
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Button(
                            onClick = onRetryWeather
                        ) {
                            Text("Reintentar clima")
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /*
         * ACCIONES
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
            modifier = Modifier.height(20.dp)
        )

        /*
         * ENTRENAMIENTOS
         */

        Text(
            text = "Entrenamientos registrados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        if (trainings.isEmpty()) {

            Text(
                text =
                    "Todavía no hay entrenamientos registrados."
            )

        } else {

            LazyColumn(
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = trainings,
                    key = { it.id }
                ) { training ->

                    Card(
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(16.dp)
                        ) {

                            Text(
                                text =
                                    "${training.style} - ${training.distance} m",
                                style =
                                    MaterialTheme.typography.titleSmall,
                                fontWeight =
                                    FontWeight.Bold
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(4.dp)
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
                                    Modifier.height(10.dp)
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