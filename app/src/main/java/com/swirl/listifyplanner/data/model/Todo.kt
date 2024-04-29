package com.swirl.listifyplanner.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val timeStamp: LocalDateTime,
    val isImportant: Boolean = false,
    val isDone: Boolean = false
): Serializable