package com.example.mobilehackertaskmanager.ui.screen.taskdetail

import com.example.mobilehackertaskmanager.ui.state.TaskDetailUiState
import kotlinx.coroutines.flow.StateFlow

interface ITaskDetailViewModel {
    val uiState: StateFlow<TaskDetailUiState>
    fun loadTask()
}