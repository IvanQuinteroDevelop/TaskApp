package com.navi.taskapp.addtasks.domain

import com.navi.taskapp.addtasks.data.TaskRepository
import com.navi.taskapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.tasks
}