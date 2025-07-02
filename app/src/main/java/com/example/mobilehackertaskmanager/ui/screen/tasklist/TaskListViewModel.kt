package com.example.mobilehackertaskmanager.ui.screen.tasklist

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import com.example.mobilehackertaskmanager.domain.utils.runUseCaseSafely
import com.example.mobilehackertaskmanager.ui.state.TaskListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val tasksUseCases: TaskUseCases
) : ITaskListViewModel, ViewModel() {

    @VisibleForTesting
    private val _uiState = MutableStateFlow(TaskListUiState())
    override val uiState: StateFlow<TaskListUiState> = _uiState

    init {
        observeTasks()
    }

    private fun observeTasks() {
        runUseCaseSafely {
            tasksUseCases.getTasks()
                .map { it.sortedBy { task -> task.id } }
                .distinctUntilChanged()
                .onEach { tasks ->
                    if(_uiState.value.tasks != tasks){
                        _uiState.value = _uiState.value.copy(
                            tasks = tasks,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    @VisibleForTesting
    override fun updateTask(task: Task) {
        viewModelScope.launch {
            runUseCaseSafely {
                tasksUseCases.updateTask(task)
            }

        }
    }

    @VisibleForTesting
    override fun deleteTask(id: String) {
        viewModelScope.launch {
            runUseCaseSafely {
                tasksUseCases.deleteTask(id)
            }

        }
    }
}