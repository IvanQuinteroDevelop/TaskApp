package com.navi.taskapp.addtasks.ui.model

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val taskName: String,
    val description: String,
    val date: String,
    val type: String,
    var selected: Boolean = false
)
