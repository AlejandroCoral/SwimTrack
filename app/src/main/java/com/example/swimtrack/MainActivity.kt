package com.example.swimtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.example.swimtrack.data.local.SwimTrackDatabase
import com.example.swimtrack.data.preferences.UserPreferencesRepository
import com.example.swimtrack.data.remote.RetrofitInstance
import com.example.swimtrack.navigation.AppNavigation
import com.example.swimtrack.repository.TrainingRepository
import com.example.swimtrack.repository.WeatherRepository
import com.example.swimtrack.ui.theme.SwimTrackTheme
import com.example.swimtrack.viewmodel.SettingsViewModel
import com.example.swimtrack.viewmodel.SettingsViewModelFactory
import com.example.swimtrack.viewmodel.TrainingViewModel
import com.example.swimtrack.viewmodel.TrainingViewModelFactory
import com.example.swimtrack.viewmodel.WeatherViewModel
import com.example.swimtrack.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var trainingViewModel: TrainingViewModel
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // -------------------------
        // ROOM
        // -------------------------

        val database =
            SwimTrackDatabase.getDatabase(
                applicationContext
            )

        val trainingRepository =
            TrainingRepository(
                database.trainingDao()
            )

        val trainingFactory =
            TrainingViewModelFactory(
                trainingRepository
            )

        trainingViewModel =
            ViewModelProvider(
                this,
                trainingFactory
            )[TrainingViewModel::class.java]

        // -------------------------
        // DATASTORE
        // -------------------------

        val preferencesRepository =
            UserPreferencesRepository(
                applicationContext
            )

        val settingsFactory =
            SettingsViewModelFactory(
                preferencesRepository
            )

        settingsViewModel =
            ViewModelProvider(
                this,
                settingsFactory
            )[SettingsViewModel::class.java]

        // -------------------------
        // RETROFIT
        // -------------------------

        val weatherRepository =
            WeatherRepository(
                RetrofitInstance.weatherApi
            )

        val weatherFactory =
            WeatherViewModelFactory(
                weatherRepository
            )

        weatherViewModel =
            ViewModelProvider(
                this,
                weatherFactory
            )[WeatherViewModel::class.java]

        // -------------------------
        // COMPOSE
        // -------------------------

        setContent {

            val darkMode by
            settingsViewModel
                .darkMode
                .collectAsState()

            SwimTrackTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {

                AppNavigation(
                    trainingViewModel = trainingViewModel,
                    settingsViewModel = settingsViewModel,
                    weatherViewModel = weatherViewModel
                )
            }
        }
    }
}