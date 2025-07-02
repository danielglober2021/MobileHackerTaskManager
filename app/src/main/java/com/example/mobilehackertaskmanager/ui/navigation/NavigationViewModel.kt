package com.example.mobilehackertaskmanager.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mobilehackertaskmanager.ui.screen.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
    var currentScreen by mutableStateOf<Screen>(Screen.TaskList)
        private set

    fun navigateToDetail(taskId: String) {
        currentScreen = Screen.TaskDetail(taskId)
    }

    fun navigateToForm() {
        currentScreen = Screen.TaskForm
    }

    fun navigateBack() {
        currentScreen = Screen.TaskList
    }
}
