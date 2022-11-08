package com.a90ms.domain.base

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Exception(val exception: kotlin.Exception) : Result<Nothing>()
}
