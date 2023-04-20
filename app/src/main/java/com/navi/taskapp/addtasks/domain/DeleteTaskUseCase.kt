package com.navi.taskapp.addtasks.domain

import com.navi.taskapp.addtasks.data.TaskRepository
import com.navi.taskapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private  val taskRepository: TaskRepository) {

    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.deleteTask(taskModel)
    }
}