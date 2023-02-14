package uz.devmi.mvvmkattabozor.base.extention

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber

fun <T> Flow<T>.flatMapResult(): Flow<Result<T>> =
    map { Result.success(it) }
        .catch { emit(Result.failure(it)) }

fun <T> Flow<Result<T>>.launchCatchingIn(scope: CoroutineScope): FlowResult<T> =
    FlowResultImpl(this).launchIn(scope)

@Suppress("unused")
suspend fun <T> Flow<Result<T>>.collectCatching(): FlowResult<T> =
    FlowResultImpl(this).collect()

interface FlowResult<T> {
    fun onStart(action: () -> Unit): FlowResultActionStart<T>
    fun onSuccess(action: (T) -> Unit): FlowResultActionSuccess<T>
    fun onFailure(action: (Throwable) -> Unit)
}

interface FlowResultActionStart<T> {
    fun onSuccess(action: (T) -> Unit): FlowResultActionSuccess<T>
    fun onFailure(action: (Throwable) -> Unit)
}

interface FlowResultActionSuccess<T> {
    fun onFailure(action: (Throwable) -> Unit)
}

private class FlowResultImpl<T>(
    private val flow: Flow<Result<T>>
) : FlowResult<T>, FlowResultActionStart<T>, FlowResultActionSuccess<T> {
    private var onStart: () -> Unit = {}
    private var onSuccess: (T) -> Unit = {}
    private var onFailure: (Throwable) -> Unit = {}

    @Suppress("EXPERIMENTAL_API_USAGE")
    suspend fun collect(): FlowResult<T> {
        flow
            .onStart {
                runCatching(onStart)
                    .onFailure {
                        Timber.e("Flow Result: onStart", "", it)
                        onFailure(it)
                    }
            }
            .collect { it ->
                it.onSuccess {
                    runCatching { onSuccess.invoke(it) }
                        .onFailure {
                            Timber.e("Flow Result: onSuccess", "", it)
                            onFailure(it)
                        }
                }

                it.onFailure {
                    if (it !is CancellationException) {
                        Timber.e("Flow Result: onFailure", "", it)
                        onFailure(it)
                    }
                }
            }

        return this
    }

    fun launchIn(scope: CoroutineScope): FlowResult<T> {
        scope.launch { collect() }
        return this
    }

    override fun onStart(action: () -> Unit): FlowResultActionStart<T> {
        onStart = action
        return this
    }

    override fun onSuccess(action: (T) -> Unit): FlowResultActionSuccess<T> {
        onSuccess = action
        return this
    }

    override fun onFailure(action: (Throwable) -> Unit) {
        onFailure = action
    }
}