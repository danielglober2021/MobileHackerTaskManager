package com.example.mobilehackertaskmanager.domain.usecase

import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.tuapp.data.repository.TaskRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(
    private val repository: TaskRepository,
    logger: Logger
): Logger by logger {
    suspend operator fun invoke(taskId: String): Task? {
        log("Se invocó getTasksByIdUseCase()")
        return repository.getTaskById(taskId)
    }
}
