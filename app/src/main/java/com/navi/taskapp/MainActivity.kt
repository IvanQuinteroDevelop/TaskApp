package com.navi.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.navi.taskapp.addtasks.ui.composables.TaskListScreen
import com.navi.taskapp.addtasks.ui.TaskViewModel
import com.navi.taskapp.addtasks.ui.composables.AddTaskScreen
import com.navi.taskapp.addtasks.ui.navigation.TaskRoutes
import com.navi.taskapp.ui.theme.TaskAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = TaskRoutes.TaskListScreen.route) {
                        composable(TaskRoutes.TaskListScreen.route) { TaskListScreen(taskViewModel = taskViewModel, navigationController)}
                        composable(TaskRoutes.TaskCreationScreen.route) { AddTaskScreen(taskViewModel = taskViewModel, navigationController)}
                    }
                }
            }
        }
    }
}