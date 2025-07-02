package com.example.mobilehackertaskmanager.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilehackertaskmanager.ui.navigation.NavigationViewModel
import com.example.mobilehackertaskmanager.ui.screen.entrypoints.TaskDetailEntryPoint
import com.example.mobilehackertaskmanager.ui.screen.taskform.TaskFormScreen
import com.example.mobilehackertaskmanager.ui.screen.taskform.TaskFormViewModel
import com.example.mobilehackertaskmanager.ui.screen.tasklist.TaskListScreen
import com.example.mobilehackertaskmanager.ui.screen.tasklist.TaskListViewModel

@Composable
fun RenderScreen(screen: Screen, innerPadding: PaddingValues, navViewModel: NavigationViewModel) {
    when (screen) {
        is Screen.TaskList -> {
            val taskListViewModel: TaskListViewModel = hiltViewModel()
            TaskListScreen(
                viewModel = taskListViewModel,
                onTaskClick = { navViewModel.navigateToDetail(it) },
                innerPadding = innerPadding,
            )
        }

        is Screen.TaskDetail -> {
            TaskDetailEntryPoint(
                taskId = screen.taskId,
                innerPadding = innerPadding,
            )
        }

        is Screen.TaskForm -> {
            val taskFormViewModel: TaskFormViewModel = hiltViewModel()
            TaskFormScreen(
                viewModel = taskFormViewModel,
                onBackSuccess = { navViewModel.navigateBack() },
                innerPadding = innerPadding,
            )
        }
    }
}
