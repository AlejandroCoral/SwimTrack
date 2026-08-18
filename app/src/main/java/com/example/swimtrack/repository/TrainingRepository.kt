package com.example.swimtrack.repository

import com.example.swimtrack.data.local.TrainingDao
import com.example.swimtrack.data.local.TrainingEntity
import kotlinx.coroutines.flow.Flow

class TrainingRepository(
    private val trainingDao: TrainingDao
) {

    val allTrainings: Flow<List<TrainingEntity>> =
        trainingDao.getAllTrainings()

    suspend fun insertTraining(training: TrainingEntity) {
        trainingDao.insertTraining(training)
    }

    suspend fun deleteTraining(training: TrainingEntity) {
        trainingDao.deleteTraining(training)
    }
}