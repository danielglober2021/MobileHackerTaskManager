package com.example.mobilehackertaskmanager.mocks

import androidx.lifecycle.ViewModel
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.ui.screen.tasklist.ITaskListViewModel
import com.example.mobilehackertaskmanager.ui.state.TaskListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeTaskListViewModel(
    initialState: TaskListUiState = TaskListUiState()
) : ITaskListViewModel {

    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<TaskListUiState> = _uiState

    fun setUiState(state: TaskListUiState) {
        _uiState.value = state
    }

    override fun updateTask(task: Task) {
        val updatedTasks = _uiState.value.tasks.map {
            if (it.id == task.id) task else it
        }
        _uiState.value = _uiState.value.copy(tasks = updatedTasks)
    }

    override fun deleteTask(taskId: String) {
        val filteredTasks = _uiState.value.tasks.filterNot { it.id == taskId }
        _uiState.value = _uiState.value.copy(tasks = filteredTasks)
    }

    fun setTasks(tasks: List<Task>) {
        _uiState.value = _uiState.value.copy(tasks = tasks, isLoading = false, error = null)
    }
}

