package com.vic.project.app_movies.utils

import android.util.Log

class AndroidLogger : Logger {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}

actual fun getLoggerInstance(): Logger = AndroidLogger()

actual val isDebug: Boolean = false
