package com.example.mobilehackertaskmanager.ui.screen.entrypoints

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilehackertaskmanager.ui.screen.taskdetail.ITaskDetailViewModel
import com.example.mobilehackertaskmanager.ui.screen.taskdetail.TaskDetailScreen
import com.example.mobilehackertaskmanager.ui.screen.taskdetail.TaskDetailViewModel
import com.example.mobilehackertaskmanager.ui.screen.taskdetail.TaskDetailViewModelFactory
import dagger.hilt.android.EntryPointAccessors

@Composable
fun TaskDetailEntryPoint(taskId: String,
                         innerPadding: PaddingValues) {
    val context = LocalContext.current

    val taskUseCases = remember {
        EntryPointAccessors
            .fromApplication(context, TaskUseCasesEntryPoint::class.java)
            .taskUseCases()
    }

    val viewModelFactory = remember(taskId) {
        TaskDetailViewModelFactory(taskId, taskUseCases)
    }

    val viewModel: ITaskDetailViewModel = viewModel(
        key = taskId,
        factory = viewModelFactory
    )

    TaskDetailScreen(
        viewModel = viewModel,
        innerPadding = innerPadding,
    )
}
