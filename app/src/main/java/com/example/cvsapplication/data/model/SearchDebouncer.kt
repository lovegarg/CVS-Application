package com.example.cvsapplication.data.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * author lgarg on 12/9/25.
 */
class SearchDebouncer(
    private val delayMs: Long
) {
    private var job: Job? = null

    fun debounce(scope: CoroutineScope, block: suspend () -> Unit) {
        job?.cancel()
        job = scope.launch {
            delay(delayMs)
            block()
        }
    }

    /** Cancel any pending action. */
    fun clear() {
        job?.cancel()
        job = null
    }
}