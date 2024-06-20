package com.example.builtbypixeltest.Utils

sealed class GenericResult<out T> {
    data class Success<out T>(val data: T) : GenericResult<T>()
    data class Error(val exception: Exception) : GenericResult<Nothing>()
    object Loading : GenericResult<Nothing>()
}