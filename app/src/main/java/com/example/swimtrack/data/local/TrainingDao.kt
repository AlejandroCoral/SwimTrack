package com.example.swimtrack.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {

    @Insert
    suspend fun insertTraining(training: TrainingEntity)

    @Delete
    suspend fun deleteTraining(training: TrainingEntity)

    @Query("SELECT * FROM trainings ORDER BY id DESC")
    fun getAllTrainings(): Flow<List<TrainingEntity>>
}