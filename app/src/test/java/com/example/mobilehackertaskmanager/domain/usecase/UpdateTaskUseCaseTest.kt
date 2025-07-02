package com.example.mobilehackertaskmanager.domain.usecase

import com.example.mobilehackertaskmanager.data.model.Task
import com.tuapp.data.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class UpdateTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: UpdateTaskUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = UpdateTaskUseCase(repository)
    }

    @Test
    fun `invoke calls repository updateTask`() = runTest {
        val task = Task(id = "1", title = "Actualizada", description = "Desc", completed = true)

        useCase(task)

        verify(repository).updateTask(task)
    }
}
