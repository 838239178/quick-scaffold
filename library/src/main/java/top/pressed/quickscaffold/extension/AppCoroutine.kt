package top.pressed.quickscaffold.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun <T> AppCompatActivity.launch(
    context: CoroutineContext = lifecycleScope.coroutineContext,
    tryBlock: suspend CoroutineScope.() -> T,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit = {},
    finallyBlock: suspend CoroutineScope.() -> Unit = {}
): Job {
    return lifecycleScope.launch(context) {
        try {
            tryBlock()
        } catch (e: Throwable) {
            catchBlock(e)
        } finally {
            finallyBlock()
        }
    }
}

fun <T> AppCompatActivity.launch(
    context: CoroutineContext = lifecycleScope.coroutineContext,
    tryBlock: suspend CoroutineScope.() -> T
): Job {
    return launch(context, tryBlock, {}, {})
}

fun <T> Fragment.launch(
    context: CoroutineContext = lifecycleScope.coroutineContext,
    tryBlock: suspend CoroutineScope.() -> T,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit = {},
    finallyBlock: suspend CoroutineScope.() -> Unit = {}
): Job {
    return lifecycleScope.launch(context) {
        try {
            tryBlock()
        } catch (e: Throwable) {
            catchBlock(e)
        } finally {
            finallyBlock()
        }
    }
}

fun <T> Fragment.launch(
    context: CoroutineContext = lifecycleScope.coroutineContext,
    tryBlock: suspend CoroutineScope.() -> T
): Job {
    return launch(context, tryBlock, {}, {})
}

fun <T> ViewModel.launch(
    context: CoroutineContext = viewModelScope.coroutineContext,
    tryBlock: suspend CoroutineScope.() -> T,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit = {},
    finallyBlock: suspend CoroutineScope.() -> Unit = {}
): Job {
    return viewModelScope.launch(context) {
        try {
            tryBlock()
        } catch (e: Throwable) {
            catchBlock(e)
        } finally {
            finallyBlock()
        }
    }
}

fun <T> ViewModel.launch(
    context: CoroutineContext = viewModelScope.coroutineContext,
    tryBlock: suspend CoroutineScope.() -> T
): Job {
    return launch(context, tryBlock, {}, {})
}