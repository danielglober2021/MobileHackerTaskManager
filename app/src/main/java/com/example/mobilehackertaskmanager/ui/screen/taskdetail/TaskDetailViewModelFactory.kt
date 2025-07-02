package com.example.mobilehackertaskmanager.ui.screen.taskdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases

class TaskDetailViewModelFactory(
    private val taskId: String,
    private val taskUseCases: TaskUseCases
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskDetailViewModel(taskId, taskUseCases) as T
    }
}