package com.example.swimtrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swimtrack.ui.screens.AddTrainingScreen
import com.example.swimtrack.ui.screens.HomeScreen
import com.example.swimtrack.ui.screens.SettingsScreen
import com.example.swimtrack.viewmodel.SettingsViewModel
import com.example.swimtrack.viewmodel.TrainingViewModel

@Composable
fun AppNavigation(
    trainingViewModel: TrainingViewModel,
    settingsViewModel: SettingsViewModel
) {

    val navController = rememberNavController()

    val trainings by trainingViewModel.trainings.collectAsState()

    val darkMode by settingsViewModel.darkMode.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {

            HomeScreen(
                trainings = trainings,

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
                }
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