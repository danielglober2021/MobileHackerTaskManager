package com.example.mobilehackertaskmanager.ui.state

import com.example.mobilehackertaskmanager.data.EMPTY_STRING

data class TaskFormUiState(
    val title: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val completed: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
