package com.a90ms.domain.base

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val code: Int? = 99,
        val errorCode: String,
        val message: String
    ) : Result<Nothing>()

    data class Exception(val exception: kotlin.Exception) : Result<Nothing>()
}
