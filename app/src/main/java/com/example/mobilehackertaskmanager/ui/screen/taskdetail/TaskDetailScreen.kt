package com.example.mobilehackertaskmanager.ui.screen.taskdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobilehackertaskmanager.R
import com.example.mobilehackertaskmanager.ui.utils.DebugRecompose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: ITaskDetailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val (isLoading, task, error) = uiState

    if (!isLoading && task == null && error.isNullOrEmpty())
        return

    Scaffold { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            DebugRecompose("TaskDetail: id_task: ${task?.id}, isLoading: ${isLoading}, error: ${error}")
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.testTag(stringResource(R.string.loading)))
                }

                error != null -> {
                    AnimatedVisibility(visible = true, enter = fadeIn()) {
                        Text(
                            modifier = Modifier.testTag(stringResource(R.string.error_with_parameter, error)),
                            text = stringResource(R.string.error_with_parameter, error),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                task != null -> {
                    AnimatedVisibility(visible = true, enter = fadeIn()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(6.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(stringResource(R.string.task_detail), style = MaterialTheme.typography.headlineSmall)

                                HorizontalDivider()

                                Column {
                                    Text(stringResource(R.string.title), style = MaterialTheme.typography.labelMedium)
                                    Text(task.title, style = MaterialTheme.typography.bodyLarge)
                                }

                                Column {
                                    Text(stringResource(R.string.description), style = MaterialTheme.typography.labelMedium)
                                    Text(task.description, style = MaterialTheme.typography.bodyLarge)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = task.completed,
                                        onCheckedChange = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(stringResource(R.string.completed))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

