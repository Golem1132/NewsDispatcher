package com.example.newsdispatcher.api

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Error<T>(val exception: Exception) : ApiOperation<T>

    fun onSuccess(onSuccess: (T) -> Unit): ApiOperation<T> {
        if (this is Success) onSuccess(data)
        return this
    }

    fun onError(onError: (Exception) -> Unit): ApiOperation<T> {
        if (this is Error) onError(exception)
        return this
    }
}