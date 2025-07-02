package com.example.mobilehackertaskmanager.ui.screen.taskform

import com.example.mobilehackertaskmanager.ui.state.TaskFormUiState
import kotlinx.coroutines.flow.StateFlow

interface ITaskFormViewModel {
    val uiState: StateFlow<TaskFormUiState>
    fun onTitleChange(newTitle: String)
    fun onDescriptionChange(newDescription: String)
    fun onCompletedChange(newCompleted: Boolean)
    fun saveTask()
    fun clearSuccess()
    fun clearForm()
}