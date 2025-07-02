package com.example.mobilehackertaskmanager.ui.screen.tasklist

import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.domain.usecase.DeleteTaskUseCase
import com.example.mobilehackertaskmanager.domain.usecase.GetTasksUseCase
import com.example.mobilehackertaskmanager.domain.usecase.TaskUseCases
import com.example.mobilehackertaskmanager.domain.usecase.UpdateTaskUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class TaskListViewModelTest {

    private lateinit var getTasks: GetTasksUseCase
    private lateinit var updateTask: UpdateTaskUseCase
    private lateinit var deleteTask: DeleteTaskUseCase
    private lateinit var useCases: TaskUseCases
    private lateinit var viewModel: TaskListViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val dummyTasks = listOf(
        Task("1", "Task 1", "Desc 1"),
        Task("2", "Task 2", "Desc 2", completed = true)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getTasks = mock()
        updateTask = mock()
        deleteTask = mock()

        useCases = TaskUseCases(
            getTasks = getTasks,
            getTaskById = mock(),
            addTask = mock(),
            deleteTask = deleteTask,
            updateTask = updateTask
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `observeTasks updates uiState with tasks`() = runTest {
        whenever(getTasks()).thenReturn(flowOf(dummyTasks))

        viewModel = TaskListViewModel(useCases)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(dummyTasks, state.tasks)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `observeTasks sets error when exception is thrown`() = runTest {
        whenever(getTasks()).thenReturn(flow { throw RuntimeException("Boom") })

        viewModel = TaskListViewModel(useCases)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Boom", state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `updateTask calls updateTask use case`() = runTest {
        whenever(getTasks()).thenReturn(flowOf(emptyList()))
        viewModel = TaskListViewModel(useCases)
        advanceUntilIdle()

        val task = Task("1", "Task", "Desc")
        viewModel.updateTask(task)
        advanceUntilIdle()

        verify(updateTask).invoke(task)
    }

    @Test
    fun `deleteTask calls deleteTask use case`() = runTest {
        whenever(getTasks()).thenReturn(flowOf(emptyList()))
        viewModel = TaskListViewModel(useCases)
        advanceUntilIdle()

        viewModel.deleteTask("abc123")
        advanceUntilIdle()

        verify(deleteTask).invoke("abc123")
    }
}
