package com.example.mobilehackertaskmanager.domain.usecase

import com.tuapp.data.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class DeleteTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: DeleteTaskUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = DeleteTaskUseCase(repository)
    }

    @Test
    fun `invoke calls repository deleteTask`() = runTest {
        val taskId = "abc123"
        useCase(taskId)
        verify(repository).deleteTask(taskId)
    }
}
