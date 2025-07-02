package com.example.mobilehackertaskmanager.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobilehackertaskmanager.ui.screen.Screen
import com.example.mobilehackertaskmanager.ui.screen.taskform.TaskFormScreen
import com.example.mobilehackertaskmanager.ui.screen.tasklist.TaskListScreen
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mobilehackertaskmanager.R
import com.example.mobilehackertaskmanager.data.EMPTY_STRING
import com.example.mobilehackertaskmanager.ui.screen.RenderScreen
import com.example.mobilehackertaskmanager.ui.screen.entrypoints.TaskDetailEntryPoint
import com.example.mobilehackertaskmanager.ui.screen.taskform.TaskFormViewModel
import com.example.mobilehackertaskmanager.ui.screen.tasklist.TaskListViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
/*@Composable
fun AppRoot() {
    val navViewModel: NavigationViewModel = hiltViewModel()
    val currentScreen = navViewModel.currentScreen

    val listVm = hiltViewModel<TaskListViewModel>()
    val formVm = hiltViewModel<TaskFormViewModel>()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedContent(targetState = currentScreen, label = stringResource(R.string.topbartitle)) { screen ->
                        Text(
                            when (screen) {
                                is Screen.TaskList -> stringResource(R.string.ethical_hacker_steps)
                                else -> EMPTY_STRING
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                },
                navigationIcon = {
                    if (currentScreen != Screen.TaskList) {
                        run {
                            IconButton(onClick = { navViewModel.navigateBack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.return_string))
                            }
                        }
                    } else null
                }
            )
        },
        floatingActionButton = {
            val showFab = currentScreen == Screen.TaskList
            val fabScale by animateFloatAsState(
                targetValue = if (showFab) 1f else 0f,
                animationSpec = tween(durationMillis = 300),
                label = stringResource(R.string.fabscale)
            )

            if (fabScale > 0f) {
                FloatingActionButton(
                    onClick = { navViewModel.navigateToForm() },
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_task))
                }
            }
        }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize()) {
            Crossfade(
                targetState = currentScreen,
                animationSpec = tween(500),
                label = "screenContentFade"
            ) { screenState ->
                when (screenState) {
                    is Screen.TaskList -> ContentWrapper {
                        TaskListScreen(listVm, navViewModel::navigateToDetail, innerPadding)
                    }
                    is Screen.TaskDetail -> ContentWrapper {
                        TaskDetailEntryPoint(screenState.taskId, innerPadding)
                    }
                    is Screen.TaskForm -> ContentWrapper {
                        TaskFormScreen(viewModel = formVm, onBackSuccess = navViewModel::navigateBack, innerPadding = innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentWrapper(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(300))
    ) {
        content()
    }
}*/

@Composable
fun AppRoot() {
    val navViewModel: NavigationViewModel = hiltViewModel()
    val currentScreen = navViewModel.currentScreen

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedContent(targetState = currentScreen, label = stringResource(R.string.topbartitle)) { screen ->
                        Text(
                            when (screen) {
                                is Screen.TaskList -> stringResource(R.string.ethical_hacker_steps)
                                else -> EMPTY_STRING
                            },
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                },
                navigationIcon = {
                    if (currentScreen != Screen.TaskList) {
                        run {
                            IconButton(onClick = { navViewModel.navigateBack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.return_string))
                            }
                        }
                    } else null
                }
            )
        },
        floatingActionButton = {
            val showFab = currentScreen == Screen.TaskList
            val fabScale by animateFloatAsState(
                targetValue = if (showFab) 1f else 0f,
                animationSpec = tween(durationMillis = 300),
                label = stringResource(R.string.fabscale)
            )

            if (fabScale > 0f) {
                FloatingActionButton(
                    onClick = { navViewModel.navigateToForm() },
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_task))
                }
            }
        }
    ) { innerPadding ->
        Box{
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    scaleIn(tween(1000), initialScale = 0.9f) +
                            fadeIn(tween(1000)) togetherWith
                            scaleOut(tween(1000), targetScale = 1.1f) +
                            fadeOut(tween(1000))
                },
                label = stringResource(R.string.zoomandfade)
            ) { screen ->
                RenderScreen(screen, innerPadding, navViewModel)
            }

        }
    }
}
