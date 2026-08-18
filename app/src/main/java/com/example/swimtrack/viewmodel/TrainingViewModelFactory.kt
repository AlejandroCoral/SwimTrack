package com.example.swimtrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.swimtrack.repository.TrainingRepository

class TrainingViewModelFactory(
    private val repository: TrainingRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(TrainingViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return TrainingViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}