package com.navi.taskapp.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.navi.taskapp.addtasks.ui.model.TaskModel

@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(initial = false)
    Box(Modifier.fillMaxSize()) {
        AddTaskDialog(showDialog,
            onDismiss = {
                taskViewModel.changeDialogValue(false)
            }, onTaskAdded = {
                taskViewModel.onCreateTask(it)
            })
        TaskList(Modifier.align(Alignment.TopStart), taskViewModel)
        FabDialog(Modifier.align(Alignment.BottomEnd), taskViewModel)
    }
}

@Composable
fun TaskList(modifier: Modifier, taskViewModel: TaskViewModel) {
    val listOfTasks = taskViewModel.tasks
    LazyColumn(modifier = modifier, content = {
        items(listOfTasks, key = { it.id }) { task ->
            ItemTask(task, taskViewModel)
        }
    })
}

@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { taskViewModel.onRemoveItem(taskModel) })
            }, elevation = 8.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = taskModel.task, modifier = Modifier.weight(1f), fontSize = 16.sp)
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { taskViewModel.onCheckBoxSelected(taskModel) })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = {
            taskViewModel.changeDialogValue(true)
        }, modifier = modifier.padding(16.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add button")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var myTasks by rememberSaveable {
        mutableStateOf("")
    }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Add your task",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = myTasks,
                    onValueChange = { myTasks = it },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    onTaskAdded(myTasks)
                    myTasks = ""
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Add task")
                }
            }
        }
    }
}