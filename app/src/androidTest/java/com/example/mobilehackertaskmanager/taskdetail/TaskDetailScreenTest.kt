package com.example.mobilehackertaskmanager.taskdetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.mocks.FakeTaskDetailViewModel
import com.example.mobilehackertaskmanager.ui.screen.taskdetail.TaskDetailScreen
import com.example.mobilehackertaskmanager.ui.state.TaskDetailUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TaskDetailScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var fakeViewModel: FakeTaskDetailViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        fakeViewModel = FakeTaskDetailViewModel()
    }

    @Test
    fun showsLoadingIndicator_whenLoading() {
        fakeViewModel.setUiState(TaskDetailUiState(isLoading = true))

        composeRule.setContent {
            TaskDetailScreen(viewModel = fakeViewModel, innerPadding = PaddingValues())
        }

        composeRule.onNodeWithTag("loading").assertExists()
    }

    @Test
    fun showsErrorMessage_whenErrorOccurs() {
        fakeViewModel.setUiState(TaskDetailUiState(error = "Error de red", isLoading = false, task = null))

        composeRule.setContent {
            TaskDetailScreen(viewModel = fakeViewModel, innerPadding = PaddingValues())
        }

        composeRule.onNodeWithTag("Error: Error de red").assertExists()
    }

    @Test
    fun showsTaskDetails_whenDataLoaded() {
        val task = Task(id = "1", title = "Análisis forense", description = "Investigar logs", completed = false)
        fakeViewModel.setUiState(TaskDetailUiState(task = task, isLoading = false))

        composeRule.setContent {
            TaskDetailScreen(viewModel = fakeViewModel, innerPadding = PaddingValues())
        }

        composeRule.onNodeWithText("Título").assertExists()
        composeRule.onNodeWithText("Análisis forense").assertExists()
        composeRule.onNodeWithText("Descripción").assertExists()
        composeRule.onNodeWithText("Investigar logs").assertExists()
        composeRule.onNodeWithText("Completada").assertExists()
    }
}
