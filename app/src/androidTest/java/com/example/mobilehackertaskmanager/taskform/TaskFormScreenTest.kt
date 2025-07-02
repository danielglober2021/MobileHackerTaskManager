package com.example.mobilehackertaskmanager.taskform

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mobilehackertaskmanager.mocks.FakeTaskFormViewModel
import com.example.mobilehackertaskmanager.ui.screen.taskform.TaskFormScreen
import com.example.mobilehackertaskmanager.ui.state.TaskFormUiState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TaskFormScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var fakeViewModel: FakeTaskFormViewModel
    private var onSuccessCalled = false

    @Before
    fun setup() {
        hiltRule.inject()
        fakeViewModel = FakeTaskFormViewModel()
        onSuccessCalled = false
    }

    @Test
    fun showsTitleAndDescriptionInputs() {
        composeRule.setContent {
            TaskFormScreen(
                viewModel = fakeViewModel,
                onBackSuccess = {},
                innerPadding = PaddingValues()
            )
        }

        composeRule.onNodeWithText("Título").assertExists()
        composeRule.onNodeWithText("Descripción").assertExists()
    }

    @Test
    fun canTypeInTitleAndDescription() {
        composeRule.setContent {
            TaskFormScreen(
                viewModel = fakeViewModel,
                onBackSuccess = {},
                innerPadding = PaddingValues()
            )
        }

        val title = "Análisis de malware"
        val description = "Revisar código sospechoso"

        composeRule.onNodeWithText("Título").performTextInput(title)
        composeRule.onNodeWithText("Descripción").performTextInput(description)

        composeRule.onNodeWithText(title).assertExists()
        composeRule.onNodeWithText(description).assertExists()
    }

    @Test
    fun checkboxCanBeToggled() {
        composeRule.setContent {
            TaskFormScreen(
                viewModel = fakeViewModel,
                onBackSuccess = {},
                innerPadding = PaddingValues()
            )
        }

        composeRule.onNodeWithTag("checkbox_completed").performClick()
        composeRule.onNodeWithTag("checkbox_completed").assertIsOn()
    }

    @Test
    fun saveTaskButtonTriggersSuccessCallback() {
        composeRule.setContent {
            TaskFormScreen(
                viewModel = fakeViewModel,
                onBackSuccess = { onSuccessCalled = true },
                innerPadding = PaddingValues()
            )
        }

        composeRule.onNodeWithText("Título").performTextInput("Nueva tarea")
        composeRule.onNodeWithText("Guardar tarea").performClick()

        fakeViewModel.setUiState(fakeViewModel.uiState.value.copy(success = true))

        composeRule.waitUntil(timeoutMillis = 1000) {
            onSuccessCalled
        }
    }

    @Test
    fun showsErrorMessageIfTitleEmpty() {
        fakeViewModel.setUiState(TaskFormUiState(error = "El título no puede estar vacío."))

        composeRule.setContent {
            TaskFormScreen(
                viewModel = fakeViewModel,
                onBackSuccess = {},
                innerPadding = PaddingValues()
            )
        }

        composeRule.onNodeWithText("El título no puede estar vacío.").assertExists()
    }

    @Test
    fun showsLoadingSpinnerWhenSaving() {
        fakeViewModel.setUiState(TaskFormUiState(isSaving = true, title = "Tarea", description = "desc"))

        composeRule.setContent {
            TaskFormScreen(
                viewModel = fakeViewModel,
                onBackSuccess = {},
                innerPadding = PaddingValues()
            )
        }

        composeRule.onNode(hasTestTag("saving_spinner")).assertExists()
    }
}
