package com.example.mobilehackertaskmanager.ui.screen.taskform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilehackertaskmanager.data.EMPTY_STRING
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import com.example.mobilehackertaskmanager.domain.utils.runUseCaseSafely
import com.example.mobilehackertaskmanager.ui.state.TaskFormUiState
import com.example.mobilehackertaskmanager.utils.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskFormViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val logger: Logger
) : ITaskFormViewModel, ViewModel(), Logger by logger {

    private val _uiState = MutableStateFlow(TaskFormUiState())
    override val uiState: StateFlow<TaskFormUiState> = _uiState.asStateFlow()

    override fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    override fun onDescriptionChange(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }

    override fun onCompletedChange(newCompleted: Boolean) {
        _uiState.update { it.copy(completed = newCompleted) }
    }

    override fun clearForm() {
        _uiState.update {
            it.copy(
                title = EMPTY_STRING,
                description = EMPTY_STRING,
                completed = false,
                success = false,
                error = EMPTY_STRING
            )
        }
    }

    override fun clearSuccess() {
        _uiState.update {
            it.copy(success = false)
        }
    }

    override fun saveTask() {
        log(SAVING_TASK)
        val state = _uiState.value
        if (state.title.isBlank()) {
            _uiState.update { it.copy(error = NOT_EMPTY_TITLE_ERROR) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }
            val task = Task(
                id = UUID.randomUUID().toString(),
                title = state.title.trim(),
                description = state.description.trim(),
                completed = state.completed
            )
            runUseCaseSafely {
                taskUseCases.addTask.invoke(task)
            }.onSuccess {
                _uiState.update { it.copy(isSaving = false, success = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isSaving = false, error = error.message ?: SAVING_ERROR) }
            }
        }
    }

    companion object {
        val NOT_EMPTY_TITLE_ERROR = "El título no puede estar vacío."
        val SAVING_ERROR = "Error al guardar"
        val SAVING_TASK = "Saving task"
    }
}
