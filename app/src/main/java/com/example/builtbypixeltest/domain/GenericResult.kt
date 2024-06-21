package com.example.builtbypixeltest.domain

sealed class GenericResult<out T> {
    data class Success<out T>(val data: T) : GenericResult<T>()
    data class Error(val exception: Exception) : GenericResult<Nothing>()
    data object Loading : GenericResult<Nothing>()
}