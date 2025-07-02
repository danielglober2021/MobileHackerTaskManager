package com.example.mobilehackertaskmanager.data.model

import com.example.mobilehackertaskmanager.data.EMPTY_STRING

data class Task(
    val id: String = EMPTY_STRING,
    val title: String = EMPTY_STRING,
    val description: String = EMPTY_STRING,
    val completed: Boolean = false
)