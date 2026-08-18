package com.example.swimtrack.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TrainingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SwimTrackDatabase : RoomDatabase() {

    abstract fun trainingDao(): TrainingDao

    companion object {

        @Volatile
        private var INSTANCE: SwimTrackDatabase? = null

        fun getDatabase(context: Context): SwimTrackDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SwimTrackDatabase::class.java,
                    "swimtrack_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}