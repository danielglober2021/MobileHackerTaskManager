package com.example.mobilehackertaskmanager.mocks

import com.example.mobilehackertaskmanager.ui.screen.taskform.ITaskFormViewModel
import com.example.mobilehackertaskmanager.ui.state.TaskFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeTaskFormViewModel(
    initialState: TaskFormUiState = TaskFormUiState()
) : ITaskFormViewModel {

    private val _uiState = MutableStateFlow(initialState)
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

    override fun saveTask() {
        if (_uiState.value.title.isBlank()) {
            _uiState.update { it.copy(error = "El título no puede estar vacío.") }
            return
        }

        _uiState.update { it.copy(isSaving = true) }
        _uiState.update { it.copy(isSaving = false, success = true) }
    }

    override fun clearSuccess() {
        _uiState.update { it.copy(success = false) }
    }

    override fun clearForm() {
        _uiState.value = TaskFormUiState()
    }

    fun setUiState(state: TaskFormUiState) {
        _uiState.value = state
    }
}
