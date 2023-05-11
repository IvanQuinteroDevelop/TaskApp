package com.navi.taskapp.addtasks.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.navi.taskapp.addtasks.ui.TaskViewModel
import com.navi.taskapp.addtasks.ui.TasksUiState
import com.navi.taskapp.addtasks.ui.model.TaskModel
import com.navi.taskapp.addtasks.ui.navigation.TaskRoutes
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(taskViewModel: TaskViewModel, navigationController: NavHostController) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TasksUiState>(
        initialValue = TasksUiState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            taskViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TasksUiState.Error -> {}
        TasksUiState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is TasksUiState.Success -> {
            Scaffold(
                topBar = {
                    TaskTopBar()
                },
                floatingActionButton = { FabAddTask(Modifier.padding(1.dp), navigationController) },
                bottomBar = {}
            ) { paddingValue ->
                Box(
                    Modifier
                        .padding(paddingValue)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    TaskList(
                        Modifier.align(Alignment.TopStart),
                        taskViewModel,
                        (uiState as TasksUiState.Success).tasks
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar() {
    TopAppBar(
        title = { Text(text = "Tasks", modifier = Modifier.padding(start = 8.dp)) },
        navigationIcon = { Icon(Icons.Filled.Menu, contentDescription = "back")},
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )
    )
}

@Composable
fun TaskList(modifier: Modifier, taskViewModel: TaskViewModel, tasks: List<TaskModel>) {

    LazyColumn(modifier = modifier, content = {
        items(tasks, key = { it.id }) { task ->
            ItemTask(task, taskViewModel)
        }
    })
}

@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 9.dp, horizontal = 19.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { taskViewModel.onRemoveItem(taskModel) })
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp), horizontalAlignment = Alignment.Start
        ) {
            Text(text = taskModel.taskName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, modifier = Modifier.padding(vertical = 8.dp))
            Text(text = taskModel.date, fontSize = 14.sp)
            Text(text = taskModel.type, fontSize = 14.sp)
        }
    }
}

@Composable
fun FabAddTask(modifier: Modifier, navigationController: NavHostController) {
    FloatingActionButton(
        onClick = { navigationController.navigate(TaskRoutes.TaskCreationScreen.route) }
        , modifier = modifier.padding(16.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add button")
    }
}
