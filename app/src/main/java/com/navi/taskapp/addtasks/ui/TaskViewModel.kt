package com.navi.taskapp.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.navi.taskapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class TaskViewModel @Inject constructor(): ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    fun changeDialogValue(showDialog: Boolean) {
        _showDialog.value = showDialog
    }

    fun onCreateTask(task: String) {
        changeDialogValue(false)
        _tasks.add(TaskModel(task = task))
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onRemoveItem(taskModel: TaskModel) {
        val taskToRemove = _tasks.find { it.id == taskModel.id }
        _tasks.remove(taskToRemove)
    }

}