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
class AddTaskUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: AddTaskUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = AddTaskUseCase(repository)
    }

    @Test
    fun `invoke calls repository addTask`() = runTest {
        val task = Task("id1", "Nueva tarea", "Descripción", completed = false)
        useCase(task)
        verify(repository).addTask(task)
    }
}
