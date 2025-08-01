package com.vic.project.app_movies.utils

import platform.Foundation.NSLog

class IOSLogger : Logger {
    override fun d(tag: String, message: String) {
        NSLog("DEBUG: $tag - $message")
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        NSLog("ERROR: $tag - $message \n${throwable?.message ?: ""}")
    }
}

actual fun getLoggerInstance(): Logger = IOSLogger()

actual val isDebug: Boolean = true