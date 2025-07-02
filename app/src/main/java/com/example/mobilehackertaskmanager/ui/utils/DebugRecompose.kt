package com.example.mobilehackertaskmanager.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mobilehackertaskmanager.utils.logger.rememberLogger

@Composable
fun DebugRecompose(tag: String) {
    var count by remember { mutableIntStateOf(0) }
    val logger = rememberLogger()
    SideEffect {
        count++
        logger.log("Recomposition -> $tag recomposed ${count} times")
    }
}
