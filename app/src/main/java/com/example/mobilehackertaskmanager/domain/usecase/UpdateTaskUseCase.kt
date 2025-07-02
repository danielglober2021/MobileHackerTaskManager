package com.example.mobilehackertaskmanager.domain.usecase

import com.example.mobilehackertaskmanager.data.model.Task
import com.tuapp.data.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) {
        repository.updateTask(task)
    }
}
