package com.navi.taskapp.addtasks.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarTimeline
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.navi.taskapp.R
import com.navi.taskapp.addtasks.ui.TaskViewModel
import com.navi.taskapp.addtasks.ui.model.TaskModel
import java.time.*
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(taskViewModel: TaskViewModel, navigationController: NavHostController) {
    var taskName by rememberSaveable { mutableStateOf("") }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    var dateSelected by rememberSaveable { mutableStateOf("Select date") }
    var timeSelected by rememberSaveable { mutableStateOf("Select time") }
    val typeOptions = listOf("Personal", "Labor")
    var selectedTypeButton by remember { mutableStateOf(typeOptions[0]) }


    val calendarState = rememberSheetState()
    val timeState = rememberSheetState()
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(disabledTimeline = CalendarTimeline.PAST),
        selection = CalendarSelection.Date { dateSelected = it.toString() })

    ClockDialog(
        state = timeState,
        config = ClockConfig(defaultTime = LocalTime.now()),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            timeSelected = "$hours : $minutes"
        })
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.add_task_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 24.dp)
        )
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.Center)
                .padding(bottom = 52.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                maxLines = 1,
                label = {
                    Text(text = stringResource(R.string.task_name_label))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                shape = RoundedCornerShape(5.dp),
                label = {
                    Text(text = stringResource(R.string.description_label))
                })


            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Type of task:", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                OutlinedButton(
                    onClick = { selectedTypeButton = typeOptions[0] },
                    shape = RoundedCornerShape(5.dp),
                    colors =
                    if (selectedTypeButton == typeOptions[0])
                        ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                     else
                        ButtonDefaults.outlinedButtonColors(),
                    modifier = Modifier
                        .padding(2.dp)
                        .width(120.dp)
                ) { Text(text = typeOptions[0]) }

                OutlinedButton(
                    onClick = { selectedTypeButton = typeOptions[1] },
                    shape = RoundedCornerShape(5.dp),
                    colors =
                    if (selectedTypeButton == typeOptions[1])
                        ButtonDefaults.buttonColors(containerColor =  MaterialTheme.colorScheme.primary)
                    else
                        ButtonDefaults.outlinedButtonColors(),
                    modifier = Modifier
                        .padding(2.dp)
                        .width(120.dp)
                ) {
                    Text(text = typeOptions[1])
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .clickable { calendarState.show() }
                        .weight(1f)
                        .padding(2.dp),
                    readOnly = true,
                    enabled = false,
                    value = dateSelected,
                    trailingIcon = { Icon(Icons.Filled.CalendarMonth, contentDescription = "Icon date") },
                    onValueChange = { dateSelected = it })
                OutlinedTextField(
                    modifier = Modifier
                        .clickable { timeState.show() }
                        .weight(1f)
                        .padding(2.dp),
                    enabled = false,
                    readOnly = true,
                    value = timeSelected,
                    trailingIcon = { Icon(Icons.Filled.Timer, contentDescription = "Icon date") },
                    onValueChange = { timeSelected = it })

            }

        }

        Row(
            Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(bottom = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedButton(
                onClick = { navigationController.popBackStack() },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier.width(160.dp)
            ) {
                Text(text = "Cancel", fontSize = 16.sp)
            }
            Button(
                modifier = Modifier.width(160.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = {
                    taskViewModel.onCreateTask(
                        TaskModel(
                            taskName = taskName,
                            description = taskDescription,
                            date = "$dateSelected, $timeSelected",
                            type = selectedTypeButton
                        )
                    )
                    navigationController.popBackStack()
                },
                enabled = taskName.isNotEmpty() && dateSelected != "Select date"
            ) {
                Text(text = "Add task", fontSize = 16.sp)
            }
        }
    }
}
