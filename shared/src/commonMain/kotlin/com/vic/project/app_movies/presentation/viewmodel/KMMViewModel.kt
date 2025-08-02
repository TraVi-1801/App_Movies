package com.vic.project.app_movies.presentation.viewmodel

import com.rickclephas.kmp.observableviewmodel.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

open class KMMViewModel : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler caught: $exception")
    }

    // Shared scope
    protected val scope = CoroutineScope(SupervisorJob() + exceptionHandler)

    override fun onCleared() {
        scope.cancel()
    }

    fun launchOnMain(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(Dispatchers.Main.immediate, block = block)
    }

    fun launchOnIO(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(Dispatchers.IO, block = block)
    }
}