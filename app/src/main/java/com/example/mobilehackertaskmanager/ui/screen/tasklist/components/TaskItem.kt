package com.example.mobilehackertaskmanager.ui.screen.tasklist.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mobilehackertaskmanager.R
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.ui.utils.DebugRecompose

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DebugRecompose("TaskItem: ${task.id}")

    val backgroundColor by animateColorAsState(
        targetValue = if (task.completed)
            MaterialTheme.colorScheme.surfaceVariant
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(500)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .animateContentSize()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (statusDot, title, description, checkbox, deleteButton) = createRefs()

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (task.completed) Color.Green else Color.Red)
                    .constrainAs(statusDot) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(statusDot.end, margin = 8.dp)
                    top.linkTo(statusDot.top)
                    end.linkTo(checkbox.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
            )

            if (task.description.isNotBlank()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.constrainAs(description) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        start.linkTo(title.start)
                        end.linkTo(deleteButton.start, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                )
            }

            Checkbox(
                checked = task.completed,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.constrainAs(checkbox) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
            )

            this@Card.AnimatedVisibility(
                visible = true,
                enter = fadeIn(tween(300)) + scaleIn(tween(300)),
                exit = fadeOut(tween(300)) + scaleOut(tween(300)),
                modifier = Modifier.constrainAs(deleteButton) {
                    top.linkTo(checkbox.bottom, margin = 8.dp)
                    end.linkTo(parent.end)
                }
            ) {
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.testTag(stringResource(R.string.delete_task))
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_task_spanish)
                    )
                }
            }
        }
    }
}

/*@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DebugRecompose("TaskItem: ${task.id}")

    val backgroundColor by animateColorAsState(
        targetValue = if (task.completed)
            MaterialTheme.colorScheme.surfaceVariant
        else
            MaterialTheme.colorScheme.surface,
        animationSpec = tween(500)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .animateContentSize()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (task.completed) Color.Green else Color.Red)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                if (task.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Checkbox(
                    checked = task.completed,
                    onCheckedChange = onCheckedChange
                )
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.testTag(stringResource(R.string.delete_task))
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_task_spanish)
                    )
                }
            }
        }
    }
}*/

