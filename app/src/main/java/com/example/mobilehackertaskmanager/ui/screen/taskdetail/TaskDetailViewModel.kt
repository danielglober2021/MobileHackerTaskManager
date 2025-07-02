package com.example.mobilehackertaskmanager.ui.screen.taskdetail


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import com.example.mobilehackertaskmanager.domain.utils.runUseCaseSafely
import com.example.mobilehackertaskmanager.ui.state.TaskDetailUiState
import com.example.mobilehackertaskmanager.utils.logger.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel (
    private val taskId: String,
    private val taskUseCases: TaskUseCases,
) : ITaskDetailViewModel, ViewModel(){

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    override val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    init {
        loadTask()
    }

    override fun loadTask() {
        viewModelScope.launch {
            runUseCaseSafely {
                val task = taskUseCases.getTaskById.invoke(taskId)
                if (task != null) {
                    _uiState.value = TaskDetailUiState(isLoading = false, task = task, error = null)
                } else {
                    _uiState.value = TaskDetailUiState(isLoading = false, task = null, error = NOT_FOUND_TASK_ERROR)
                }
            }
        }
    }

    companion object{
        val NOT_FOUND_TASK_ERROR = "Tarea no encontrada"
    }
}
