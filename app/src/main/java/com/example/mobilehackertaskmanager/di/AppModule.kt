package com.example.mobilehackertaskmanager.di

import com.example.mobilehackertaskmanager.domain.usecase.AddTaskUseCase
import com.example.mobilehackertaskmanager.domain.usecase.DeleteTaskUseCase
import com.example.mobilehackertaskmanager.domain.usecase.GetTaskByIdUseCase
import com.example.mobilehackertaskmanager.domain.usecase.GetTasksUseCase
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import com.example.mobilehackertaskmanager.domain.usecase.UpdateTaskUseCase
import com.example.mobilehackertaskmanager.utils.logger.ConsoleLogger
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.google.firebase.firestore.FirebaseFirestore
import com.tuapp.data.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTaskRepository(
        firestore: FirebaseFirestore,
        logger: Logger
    ): TaskRepository = TaskRepository(firestore, logger)

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideTaskUseCases(
        repository: TaskRepository,
        logger: Logger
    ): TaskUseCases {
        return TaskUseCases(
            getTasks = GetTasksUseCase(repository, logger),
            getTaskById = GetTaskByIdUseCase(repository, logger),
            addTask = AddTaskUseCase(repository),
            deleteTask = DeleteTaskUseCase(repository),
            updateTask = UpdateTaskUseCase(repository)
        )
    }

    @Provides
    fun provideLogger(): Logger = ConsoleLogger()

}