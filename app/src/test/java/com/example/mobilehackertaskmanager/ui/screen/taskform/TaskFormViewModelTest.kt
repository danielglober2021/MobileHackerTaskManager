package com.example.mobilehackertaskmanager.ui.screen.taskform

import com.example.mobilehackertaskmanager.domain.usecase.AddTaskUseCase
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import com.example.mobilehackertaskmanager.utils.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class TaskFormViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var useCases: TaskUseCases
    private lateinit var logger: Logger
    private lateinit var addTaskUseCase: AddTaskUseCase

    private lateinit var viewModel: TaskFormViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        addTaskUseCase = mock()
        logger = mock()
        useCases = TaskUseCases(
            getTasks = mock(),
            getTaskById = mock(),
            addTask = addTaskUseCase,
            deleteTask = mock(),
            updateTask = mock()
        )

        viewModel = TaskFormViewModel(useCases, logger)
    }

    @Test
    fun `onTitleChange updates title`() {
        viewModel.onTitleChange("Nueva tarea")
        assertEquals("Nueva tarea", viewModel.uiState.value.title)
    }

    @Test
    fun `onDescriptionChange updates description`() {
        viewModel.onDescriptionChange("Descripción")
        assertEquals("Descripción", viewModel.uiState.value.description)
    }

    @Test
    fun `onCompletedChange updates completed`() {
        viewModel.onCompletedChange(true)
        assertTrue(viewModel.uiState.value.completed)
    }

    @Test
    fun `clearForm resets all fields`() {
        viewModel.onTitleChange("Título")
        viewModel.onDescriptionChange("Desc")
        viewModel.onCompletedChange(true)

        viewModel.clearForm()

        val state = viewModel.uiState.value
        assertEquals("", state.title)
        assertEquals("", state.description)
        assertFalse(state.completed)
        assertFalse(state.success)
        assertEquals("", state.error)
    }

    @Test
    fun `clearSuccess sets success to false`() {
        // viewModel.uiState.update { it.copy(success = true) }

        viewModel.clearSuccess()

        assertFalse(viewModel.uiState.value.success)
    }

    @Test
    fun `saveTask with blank title sets error`() = runTest {
        viewModel.onTitleChange("")

        viewModel.saveTask()

        val error = viewModel.uiState.value.error
        assertEquals("El título no puede estar vacío.", error)
    }

    @Test
    fun `saveTask with valid input sets success true`() = runTest {
        whenever(addTaskUseCase.invoke(any())).thenAnswer {}

        viewModel.onTitleChange("Título")
        viewModel.onDescriptionChange("Desc")
        viewModel.onCompletedChange(true)

        viewModel.saveTask()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.success)
        assertFalse(state.isSaving)
        verify(addTaskUseCase).invoke(check {
            assertEquals("Título", it.title)
            assertEquals("Desc", it.description)
            assertTrue(it.completed)
        })
    }

    @Test
    fun `saveTask when repository throws sets error`() = runTest {
        whenever(addTaskUseCase.invoke(any())).thenThrow(RuntimeException("Error"))

        viewModel.onTitleChange("Valida")
        viewModel.saveTask()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Error", state.error)
        assertFalse(state.success)
        assertFalse(state.isSaving)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
