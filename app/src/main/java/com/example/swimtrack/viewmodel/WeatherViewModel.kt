package com.example.swimtrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swimtrack.data.remote.WeatherResponse
import com.example.swimtrack.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WeatherUiState {

    object Loading : WeatherUiState()

    data class Success(
        val weather: WeatherResponse
    ) : WeatherUiState()

    data class Error(
        val message: String
    ) : WeatherUiState()
}

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<WeatherUiState>(
            WeatherUiState.Loading
        )

    val uiState: StateFlow<WeatherUiState> =
        _uiState.asStateFlow()

    fun loadWeather(
        latitude: Double,
        longitude: Double
    ) {

        viewModelScope.launch {

            _uiState.value =
                WeatherUiState.Loading

            try {

                val weather =
                    repository.getCurrentWeather(
                        latitude = latitude,
                        longitude = longitude
                    )

                _uiState.value =
                    WeatherUiState.Success(
                        weather
                    )

            } catch (e: Exception) {

                _uiState.value =
                    WeatherUiState.Error(
                        message =
                            "No se pudo obtener el clima. Revisa tu conexión a Internet."
                    )
            }
        }
    }
}