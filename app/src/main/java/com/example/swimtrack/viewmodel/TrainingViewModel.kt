package com.example.swimtrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swimtrack.data.local.TrainingEntity
import com.example.swimtrack.repository.TrainingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TrainingViewModel(
    private val repository: TrainingRepository
) : ViewModel() {

    val trainings: StateFlow<List<TrainingEntity>> =
        repository.allTrainings.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertTraining(training: TrainingEntity) {
        viewModelScope.launch {
            repository.insertTraining(training)
        }
    }

    fun deleteTraining(training: TrainingEntity) {
        viewModelScope.launch {
            repository.deleteTraining(training)
        }
    }
}