package com.example.swimtrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swimtrack.repository.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LocationUiState {

    object Idle : LocationUiState()

    object Loading : LocationUiState()

    data class Success(
        val latitude: Double,
        val longitude: Double
    ) : LocationUiState()

    data class Error(
        val message: String
    ) : LocationUiState()
}

class LocationViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<LocationUiState>(
            LocationUiState.Idle
        )

    val uiState: StateFlow<LocationUiState> =
        _uiState.asStateFlow()

    fun loadCurrentLocation() {

        viewModelScope.launch {

            _uiState.value =
                LocationUiState.Loading

            val location =
                repository.getCurrentLocation()

            if (location != null) {

                _uiState.value =
                    LocationUiState.Success(
                        latitude = location.first,
                        longitude = location.second
                    )

            } else {

                _uiState.value =
                    LocationUiState.Error(
                        "No se pudo obtener la ubicación del dispositivo."
                    )
            }
        }
    }
}