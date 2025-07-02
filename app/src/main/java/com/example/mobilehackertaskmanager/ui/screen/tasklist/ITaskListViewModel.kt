package com.example.mobilehackertaskmanager.ui.screen.tasklist

import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.ui.state.TaskListUiState
import kotlinx.coroutines.flow.StateFlow

interface ITaskListViewModel {
    val uiState: StateFlow<TaskListUiState>
    fun updateTask(task: Task)
    fun deleteTask(id: String)
}