package com.example.mobilehackertaskmanager.tasklist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.mocks.FakeTaskListViewModel
import com.example.mobilehackertaskmanager.ui.screen.tasklist.TaskListScreen
import com.example.mobilehackertaskmanager.ui.state.TaskListUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TaskListScreenInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var fakeTaskViewModel: FakeTaskListViewModel

    @Before
    fun setup() {
        fakeTaskViewModel = FakeTaskListViewModel()
        fakeTaskViewModel.setTasks(
            listOf(
                Task(id = "1", title = "Escaneo de puertos", description = "Escaneo de puertos TCP/UDP", completed = false),
                Task(id = "2", title = "Reversing básico", completed = true)
            )
        )
    }



    @Test
    fun showsTasksCorrectly() {

        composeTestRule.setContent {
            TaskListScreen(
                viewModel = fakeTaskViewModel,
                onTaskClick = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithText("Escaneo de puertos").assertExists()
        composeTestRule.onNodeWithText("Reversing básico").assertExists()
    }

    @Test
    fun showsEmptyMessageWhenNoTasks() {
        fakeTaskViewModel.setTasks(emptyList())

        composeTestRule.setContent {
            TaskListScreen(
                viewModel = fakeTaskViewModel,
                onTaskClick = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithText("No hay tareas aún.").assertExists()
    }

    @Test
    fun showsLoadingIndicator() {
        fakeTaskViewModel.setUiState(TaskListUiState(isLoading = true))

        composeTestRule.setContent {
            TaskListScreen(
                viewModel = fakeTaskViewModel,
                onTaskClick = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithTag("loading").assertExists()

    }

    @Test
    fun showsErrorMessage() {
        fakeTaskViewModel.setUiState(TaskListUiState(error = "Algo falló", isLoading = false, tasks = emptyList()))

        composeTestRule.setContent {
            TaskListScreen(
                viewModel = fakeTaskViewModel,
                onTaskClick = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithTag("Error: Algo falló").assertExists()
    }

    @Test
    fun testTaskCheckboxChange_updatesTaskCompletion() {
        val task = Task(id = "1", title = "Tarea de prueba", completed = false)
        fakeTaskViewModel.setTasks(listOf(task))

        composeTestRule.setContent {
            TaskListScreen(
                viewModel = fakeTaskViewModel,
                onTaskClick = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule
            .onAllNodes(isToggleable())[0]
            .performClick()

        val updatedTask = fakeTaskViewModel.uiState.value.tasks.first()
        assert(updatedTask.completed)
    }

    @Test
    fun testDeleteButtonClick_removesTask() {
        val task = Task(id = "1", title = "Eliminarme")
        fakeTaskViewModel.setTasks(listOf(task))

        composeTestRule.setContent {
            TaskListScreen(
                viewModel = fakeTaskViewModel,
                onTaskClick = {},
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithTag("Delete task").performClick()

        // Verifica que la tarea ya no esté
        assert(fakeTaskViewModel.uiState.value.tasks.none { it.id == "1" })
    }

    @Test
    fun testTaskItemClick_invokesCallback() {
        var clickedTaskId: String? = null

        fakeTaskViewModel.setTasks(
            listOf(Task("1", "Escaneo de puertos", "TCP/UDP"))
        )

        composeTestRule.setContent {
            TaskListScreen(
                viewModel = fakeTaskViewModel,
                onTaskClick = { clickedTaskId = it },
                innerPadding = PaddingValues()
            )
        }

        composeTestRule.onNodeWithText("Escaneo de puertos").performClick()
        assert(clickedTaskId == "1")
    }
}
