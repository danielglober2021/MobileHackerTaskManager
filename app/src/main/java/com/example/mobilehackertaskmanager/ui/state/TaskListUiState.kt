package com.example.mobilehackertaskmanager.ui.state

import com.example.mobilehackertaskmanager.data.model.Task

data class TaskListUiState(
    val isLoading: Boolean = true,
    val tasks: List<Task> = emptyList(),
    val error: String? = null
)