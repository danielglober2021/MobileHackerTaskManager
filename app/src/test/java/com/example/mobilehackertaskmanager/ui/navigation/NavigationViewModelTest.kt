package com.example.mobilehackertaskmanager.ui.navigation

import com.example.mobilehackertaskmanager.ui.screen.Screen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NavigationViewModelTest {

    private lateinit var viewModel: NavigationViewModel

    @Before
    fun setUp() {
        viewModel = NavigationViewModel()
    }

    @Test
    fun `initial screen is TaskList`() {
        assertEquals(Screen.TaskList, viewModel.currentScreen)
    }

    @Test
    fun `navigateToDetail sets currentScreen to TaskDetail`() {
        val taskId = "123"
        viewModel.navigateToDetail(taskId)

        assertEquals(Screen.TaskDetail(taskId), viewModel.currentScreen)
    }

    @Test
    fun `navigateToForm sets currentScreen to TaskForm`() {
        viewModel.navigateToForm()

        assertEquals(Screen.TaskForm, viewModel.currentScreen)
    }

    @Test
    fun `navigateBack sets currentScreen to TaskList`() {
        viewModel.navigateToDetail("456")
        viewModel.navigateBack()

        assertEquals(Screen.TaskList, viewModel.currentScreen)
    }
}
