package com.example.recyclerwithscreentransition.model

sealed class ResultWrapper<T> {
    class Loading<T>: ResultWrapper<T>()
    data class Success<T>(val data: T): ResultWrapper<T>()
    data class Failed<T>(val msg: String, val e: Throwable?): ResultWrapper<T>()
}