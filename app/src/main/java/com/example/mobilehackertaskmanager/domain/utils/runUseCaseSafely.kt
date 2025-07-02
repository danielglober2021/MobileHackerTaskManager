package com.example.mobilehackertaskmanager.domain.utils

import com.example.mobilehackertaskmanager.data.FAILED_USECASE_EXECUTION

inline fun <T> runUseCaseSafely(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Exception) {
        println("$FAILED_USECASE_EXECUTION ${e.message}")
        Result.failure(e)
    }
}
