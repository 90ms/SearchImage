package com.a90ms.domain.base

inline fun <reified T> Result<T>.onSuccess(action: (data: T) -> Unit): Result<T> {
    if (this is Result.Success) {
        action(data)
    }

    return this
}

inline fun <reified T> Result<T>.onError(
    action: (code: String, message: String) -> Unit
): Result<T> {
    if (this is Result.Error && code in 200..500) {
        action(errorCode, message)
    }

    return this
}

inline fun <reified T> Result<T>.onException(
    action: (exception: Exception) -> Unit
): Result<T> {
    if (this is Result.Exception) {
        action(exception)
    }

    return this
}
