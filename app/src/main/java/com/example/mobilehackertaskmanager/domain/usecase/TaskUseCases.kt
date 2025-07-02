package com.example.mobilehackertaskmanager.domain.usecase

import javax.inject.Inject

data class TaskUseCases @Inject constructor(
    val getTasks: GetTasksUseCase,
    val getTaskById: GetTaskByIdUseCase,
    val addTask: AddTaskUseCase,
    val deleteTask: DeleteTaskUseCase,
    val updateTask: UpdateTaskUseCase
)
