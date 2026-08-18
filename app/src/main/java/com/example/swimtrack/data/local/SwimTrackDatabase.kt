package com.example.swimtrack.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TrainingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SwimTrackDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao
}