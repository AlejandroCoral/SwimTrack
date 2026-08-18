package com.example.swimtrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val style: String,
    val distance: Int,
    val time: String,
    val date: String,
    val observation: String
)