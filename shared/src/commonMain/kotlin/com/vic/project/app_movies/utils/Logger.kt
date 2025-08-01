package com.vic.project.app_movies.utils

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}

expect fun getLoggerInstance(): Logger

expect val isDebug: Boolean