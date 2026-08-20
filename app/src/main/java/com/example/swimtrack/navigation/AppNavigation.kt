package com.example.swimtrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swimtrack.ui.screens.AddTrainingScreen
import com.example.swimtrack.ui.screens.HomeScreen
import com.example.swimtrack.ui.screens.SettingsScreen
import com.example.swimtrack.viewmodel.SettingsViewModel
import com.example.swimtrack.viewmodel.TrainingViewModel
import com.example.swimtrack.viewmodel.WeatherViewModel
import com.example.swimtrack.viewmodel.LocationUiState
import com.example.swimtrack.viewmodel.LocationViewModel

@Composable
fun AppNavigation(
    trainingViewModel: TrainingViewModel,
    settingsViewModel: SettingsViewModel,
    weatherViewModel: WeatherViewModel,
    locationViewModel: LocationViewModel
) {

    val navController =
        rememberNavController()

    val trainings by
    trainingViewModel
        .trainings
        .collectAsState()

    val darkMode by
    settingsViewModel
        .darkMode
        .collectAsState()

    val weatherUiState by
    weatherViewModel
        .uiState
        .collectAsState()

    val locationUiState by
    locationViewModel
        .uiState
        .collectAsState()

    /*
     * Por ahora usamos coordenadas fijas de Quito.
     *
     * Más adelante estas coordenadas
     * vendrán del GPS del celular.
     */

    LaunchedEffect(locationUiState) {

        if (locationUiState is LocationUiState.Success) {

            val location =
                locationUiState as LocationUiState.Success

            weatherViewModel.loadWeather(
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {

            HomeScreen(
                trainings = trainings,
                weatherUiState = weatherUiState,
                locationUiState = locationUiState,

                onAddTraining = {
                    navController.navigate(
                        "add_training"
                    )
                },

                onSettings = {
                    navController.navigate(
                        "settings"
                    )
                },

                onDeleteTraining = { training ->
                    trainingViewModel.deleteTraining(
                        training
                    )
                },

                onRetryWeather = {

                    weatherViewModel.loadWeather(
                        latitude = -0.1807,
                        longitude = -78.4678
                    )
                },

                onRequestLocation = {
                    locationViewModel.loadCurrentLocation()
                },
            )
        }

        composable("add_training") {

            AddTrainingScreen(

                onSaveTraining = { training ->

                    trainingViewModel.insertTraining(
                        training
                    )

                    navController.popBackStack()
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("settings") {

            SettingsScreen(
                darkMode = darkMode,

                onDarkModeChange =
                    settingsViewModel::setDarkMode,

                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}