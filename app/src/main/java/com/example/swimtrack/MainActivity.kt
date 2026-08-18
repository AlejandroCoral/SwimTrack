package com.example.swimtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.swimtrack.data.local.SwimTrackDatabase
import com.example.swimtrack.navigation.AppNavigation
import com.example.swimtrack.repository.TrainingRepository
import com.example.swimtrack.ui.theme.SwimTrackTheme
import com.example.swimtrack.viewmodel.TrainingViewModel
import com.example.swimtrack.viewmodel.TrainingViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var trainingViewModel: TrainingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val database = SwimTrackDatabase.getDatabase(applicationContext)

        val repository = TrainingRepository(
            database.trainingDao()
        )

        val factory = TrainingViewModelFactory(repository)

        trainingViewModel = ViewModelProvider(
            this,
            factory
        )[TrainingViewModel::class.java]

        setContent {

            SwimTrackTheme {

                AppNavigation(
                    trainingViewModel = trainingViewModel
                )
            }
        }
    }
}