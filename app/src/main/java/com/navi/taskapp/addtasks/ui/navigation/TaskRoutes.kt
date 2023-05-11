package com.navi.taskapp.addtasks.ui.navigation

sealed class TaskRoutes(val route: String) {
    object TaskListScreen:TaskRoutes("task_list_screen")
    object TaskCreationScreen:TaskRoutes("task_creation_screen")
}
