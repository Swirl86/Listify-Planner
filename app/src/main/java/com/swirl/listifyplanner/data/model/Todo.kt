package com.swirl.listifyplanner.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val todo: String,
    val timeStamp: String,
    val isCompleted: Boolean = false,
    val isImportant: Boolean = false
)