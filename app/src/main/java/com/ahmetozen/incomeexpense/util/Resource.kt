package com.ahmetozen.incomeexpense.util

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data object Loading : Resource<Nothing>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
}