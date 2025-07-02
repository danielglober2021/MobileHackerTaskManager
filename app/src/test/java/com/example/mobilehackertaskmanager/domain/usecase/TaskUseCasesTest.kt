package com.example.mobilehackertaskmanager.domain.usecase

import app.cash.turbine.test
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.tuapp.data.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class TaskUseCasesTest {

    private lateinit var repository: TaskRepository
    private lateinit var logger: Logger

    private lateinit var getTasks: GetTasksUseCase
    private lateinit var getTaskById: GetTaskByIdUseCase
    private lateinit var addTask: AddTaskUseCase
    private lateinit var deleteTask: DeleteTaskUseCase
    private lateinit var updateTask: UpdateTaskUseCase

    @Before
    fun setUp() {
        repository = mock()
        logger = mock()

        getTasks = GetTasksUseCase(repository, logger)
        getTaskById = GetTaskByIdUseCase(repository, logger)
        addTask = AddTaskUseCase(repository)
        deleteTask = DeleteTaskUseCase(repository)
        updateTask = UpdateTaskUseCase(repository)
    }

    @Test
    fun `getTasks returns flow of tasks`() = runTest {
        val expectedTasks = listOf(
            Task("1", "Título 1", "Desc 1", false),
            Task("2", "Título 2", "Desc 2", true)
        )

        whenever(repository.getTasks()).thenReturn(flowOf(expectedTasks))

        getTasks().test {
            val result = awaitItem()
            assertEquals(expectedTasks, result)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getTaskById returns a task when found`() = runTest {
        val expectedTask = Task("42", "Análisis", "Analizar vulnerabilidad", false)

        whenever(repository.getTaskById("42")).thenReturn(expectedTask)

        val result = getTaskById("42")
        assertEquals(expectedTask, result)
    }

    @Test
    fun `addTask calls repository with correct task`() = runTest {
        val task = Task("99", "Nueva", "Tarea nueva", false)

        addTask(task)

        verify(repository).addTask(task)
    }

    @Test
    fun `deleteTask calls repository with correct id`() = runTest {
        deleteTask("88")

        verify(repository).deleteTask("88")
    }

    @Test
    fun `updateTask calls repository with correct task`() = runTest {
        val task = Task("3", "Editar", "Editar tarea", true)

        updateTask(task)

        verify(repository).updateTask(task)
    }
}
