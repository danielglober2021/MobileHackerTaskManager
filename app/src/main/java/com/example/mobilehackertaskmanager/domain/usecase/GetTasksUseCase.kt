package com.example.mobilehackertaskmanager.domain.usecase

import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.tuapp.data.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository,
    logger: Logger
): Logger by logger {
    operator fun invoke(): Flow<List<Task>> {
        log("Se invocó getTasksUseCase()")
        return repository.getTasks()
    }
}
