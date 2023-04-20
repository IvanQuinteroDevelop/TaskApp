package com.navi.taskapp.addtasks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navi.taskapp.addtasks.domain.AddTaskUseCase
import com.navi.taskapp.addtasks.domain.DeleteTaskUseCase
import com.navi.taskapp.addtasks.domain.GetTaskUseCase
import com.navi.taskapp.addtasks.domain.UpdateTaskUseCase
import com.navi.taskapp.addtasks.ui.TasksUiState.*
import com.navi.taskapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = getTaskUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog


    fun changeDialogValue(showDialog: Boolean) {
        _showDialog.value = showDialog
    }

    fun onCreateTask(task: String) {
        changeDialogValue(false)

        viewModelScope.launch {
            addTaskUseCase.invoke(TaskModel(task = task))
        }
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase.invoke(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onRemoveItem(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase.invoke(taskModel)
        }
    }

}