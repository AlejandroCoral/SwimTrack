package com.example.swimtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.swimtrack.navigation.AppNavigation
import com.example.swimtrack.ui.theme.SwimTrackTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            SwimTrackTheme {
                AppNavigation()
            }
        }
    }
}