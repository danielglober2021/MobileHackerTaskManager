package com.example.mobilehackertaskmanager.ui.state

import com.example.mobilehackertaskmanager.data.model.Task

data class TaskDetailUiState(
    val isLoading: Boolean = true,
    val task: Task? = null,
    val error: String? = null
)
