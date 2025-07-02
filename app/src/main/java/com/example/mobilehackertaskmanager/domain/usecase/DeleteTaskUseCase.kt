package com.example.mobilehackertaskmanager.domain.usecase

import com.tuapp.data.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: String) {
        repository.deleteTask(taskId)
    }
}
