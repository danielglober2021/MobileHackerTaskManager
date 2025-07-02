package com.example.mobilehackertaskmanager.ui.screen

import com.example.mobilehackertaskmanager.data.EMPTY_STRING

sealed class Screen {
    object TaskList : Screen()
    object TaskForm : Screen()
    data class TaskDetail(val taskId: String = EMPTY_STRING) : Screen()
}
