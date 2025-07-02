package com.example.mobilehackertaskmanager.utils.logger

import javax.inject.Inject

interface Logger {
    fun log(message: String)
}

class ConsoleLogger @Inject constructor() : Logger {
    override fun log(message: String) = println("[Log]: $message")
}