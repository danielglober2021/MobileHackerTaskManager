package com.example.mobilehackertaskmanager.domain.usecase

import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.tuapp.data.repository.TaskRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetTaskByIdUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskByIdUseCase
    private lateinit var logger: Logger

    @Before
    fun setup() {
        repository = mock()
        logger = mock()
        useCase = GetTaskByIdUseCase(repository, logger)
    }

    @Test
    fun `invoke returns task from repository`() = runTest {
        val taskId = "task123"
        val expectedTask = Task(id = taskId, title = "Prueba", description = "Desc", completed = false)

        whenever(repository.getTaskById(taskId)).thenReturn(expectedTask)

        val result = useCase(taskId)

        assertEquals(expectedTask, result)
        verify(repository).getTaskById(taskId)
    }

    @Test
    fun `invoke returns null when repository returns null`() = runTest {
        val taskId = "task456"
        whenever(repository.getTaskById(taskId)).thenReturn(null)

        val result = useCase(taskId)

        assertNull(result)
        verify(repository).getTaskById(taskId)
    }
}
