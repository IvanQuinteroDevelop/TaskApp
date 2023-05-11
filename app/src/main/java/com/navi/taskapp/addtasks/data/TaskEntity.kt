package com.navi.taskapp.addtasks.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val taskName: String,
    val description: String,
    val date: String,
    val type: String,
    val selected: Boolean = false
)
