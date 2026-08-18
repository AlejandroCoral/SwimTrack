package com.example.swimtrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swimtrack.ui.screens.AddTrainingScreen
import com.example.swimtrack.ui.screens.HomeScreen
import com.example.swimtrack.ui.screens.SettingsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onAddTraining = {
                    navController.navigate("add_training")
                },
                onSettings = {
                    navController.navigate("settings")
                }
            )
        }

        composable("add_training") {
            AddTrainingScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}