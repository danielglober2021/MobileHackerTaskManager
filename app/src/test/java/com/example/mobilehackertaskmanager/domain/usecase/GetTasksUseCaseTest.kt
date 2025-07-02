package com.example.mobilehackertaskmanager.domain.usecase

import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.tuapp.data.repository.TaskRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetTasksUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTasksUseCase
    private lateinit var logger: Logger

    @Before
    fun setup() {
        repository = mock()
        logger = mock()
        useCase = GetTasksUseCase(repository, logger)
    }

    @Test
    fun `invoke emits list of tasks from repository`() = runTest {
        val tasks = listOf(
            Task(id = "1", title = "Tarea 1", description = "Desc 1"),
            Task(id = "2", title = "Tarea 2", description = "Desc 2")
        )

        whenever(repository.getTasks()).thenReturn(flowOf(tasks))

        val result = useCase().first()

        assertEquals(tasks, result)
        verify(repository).getTasks()
    }

    @Test
    fun `invoke emits empty list when repository returns empty list`() = runTest {
        whenever(repository.getTasks()).thenReturn(flowOf(emptyList()))
        val result = useCase().first()
        assertTrue(result.isEmpty())
        verify(repository).getTasks()
    }
}
