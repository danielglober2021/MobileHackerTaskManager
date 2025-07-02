package com.example.mobilehackertaskmanager.ui.screen.entrypoints

import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TaskUseCasesEntryPoint {
    fun taskUseCases(): TaskUseCases
}
