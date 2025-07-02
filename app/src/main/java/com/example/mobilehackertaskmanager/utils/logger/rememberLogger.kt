package com.example.mobilehackertaskmanager.utils.logger

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dagger.hilt.android.EntryPointAccessors

@Composable
fun rememberLogger(): Logger {
    val context = LocalContext.current
    return EntryPointAccessors.fromApplication(
        context.applicationContext,
        LoggerEntryPoint::class.java
    ).logger()
}