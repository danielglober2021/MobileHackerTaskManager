package com.example.mobilehackertaskmanager.mocks

import com.example.mobilehackertaskmanager.ui.screen.taskdetail.ITaskDetailViewModel
import com.example.mobilehackertaskmanager.ui.state.TaskDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeTaskDetailViewModel(
    initialState: TaskDetailUiState = TaskDetailUiState()
) : ITaskDetailViewModel {

    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()
    override fun loadTask() {

    }

    fun setUiState(state: TaskDetailUiState) {
        _uiState.value = state
    }
}