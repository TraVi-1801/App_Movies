package com.vic.project.app_movies.utils

import platform.Foundation.NSLog
import kotlin.experimental.ExperimentalNativeApi

class IOSLogger : Logger {
    override fun d(tag: String, message: String) {
        NSLog("DEBUG: $tag - $message")
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        NSLog("ERROR: $tag - $message \n${throwable?.message ?: ""}")
    }
}

actual fun getLoggerInstance(): Logger = IOSLogger()

@OptIn(ExperimentalNativeApi::class)
actual val isDebug: Boolean = Platform.isDebugBinary