package com.example.mobilehackertaskmanager.ui.screen.taskdetail

import app.cash.turbine.test
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.domain.usecase.GetTaskByIdUseCase
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalCoroutinesApi::class)
class TaskDetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var getTaskByIdUseCase: GetTaskByIdUseCase
    private lateinit var taskUseCases: TaskUseCases
    private lateinit var viewModel: TaskDetailViewModel

    private val dummyTask = Task(
        id = "abc123",
        title = "Análisis forense",
        description = "Analizar logs y trazas",
        completed = false
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        getTaskByIdUseCase = mock()

        taskUseCases = TaskUseCases(
            getTasks = mock(),
            getTaskById = getTaskByIdUseCase,
            addTask = mock(),
            deleteTask = mock(),
            updateTask = mock()
        )
    }

    @Test
    fun `uiState has task when getTaskById returns result`() = runTest {
        whenever(getTaskByIdUseCase("abc123")).thenReturn(dummyTask)

        viewModel = TaskDetailViewModel("abc123", taskUseCases)

        val state = viewModel.uiState.first()

        assertFalse(state.isLoading)
        assertEquals(dummyTask, state.task)
        assertNull(state.error)
    }

    @Test
    fun `uiState has error when getTaskById returns null`() = runTest {
        whenever(getTaskByIdUseCase("not_found")).thenReturn(null)

        viewModel = TaskDetailViewModel("not_found", taskUseCases)

        val state = viewModel.uiState.first()

        assertFalse(state.isLoading)
        assertNull(state.task)
        assertEquals("Tarea no encontrada", state.error)
    }

    @Test
    fun `loadTask updates state correctly`() = runTest {
        whenever(getTaskByIdUseCase("abc123")).thenReturn(dummyTask)

        viewModel = TaskDetailViewModel("abc123", taskUseCases)

        val final = viewModel.uiState.first { !it.isLoading }

        assertEquals(dummyTask, final.task)
        assertNull(final.error)
    }

}
