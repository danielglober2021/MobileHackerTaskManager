package com.example.mobilehackertaskmanager.ui.screen.tasklist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobilehackertaskmanager.R
import com.example.mobilehackertaskmanager.ui.screen.tasklist.components.TaskItem
import com.example.mobilehackertaskmanager.ui.utils.DebugRecompose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: ITaskListViewModel,
    onTaskClick: (String) -> Unit,
    innerPadding: PaddingValues = PaddingValues()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold (
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            val (isLoading, tasks, error) = uiState

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(48.dp).testTag(
                            stringResource(R.string.loading)
                        ))
                    }
                }

                error != null -> {
                    Text(
                        text =
                        stringResource(R.string.error_with_parameter, error),
                        modifier = Modifier.testTag(stringResource(R.string.error_with_parameter, error)),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    // Crossfade(targetState = tasks) { list ->
                        if (tasks.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.no_tasks),
                                    tint = Color.Gray,
                                    modifier = Modifier.size(64.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    stringResource(R.string.not_tasks_yet),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(tasks, key = { it.id }) { task ->
                                    val currentTask by rememberUpdatedState(task)
                                    val onClick = remember(currentTask.id) {
                                        { onTaskClick(currentTask.id) }
                                    }
                                    val onCheckedChange = remember(currentTask.id, currentTask.completed) {
                                        { isChecked: Boolean ->
                                            viewModel.updateTask(currentTask.copy(completed = isChecked))
                                        }
                                    }
                                    val onDeleteClick = remember(currentTask.id) {
                                        { viewModel.deleteTask(currentTask.id) }
                                    }

                                    TaskItem(
                                        task = currentTask,
                                        onClick = onClick,
                                        onCheckedChange = onCheckedChange,
                                        onDeleteClick = onDeleteClick
                                    )
                                }
                            }

                        // }
                    }
                }
            }
        }
    }
}
