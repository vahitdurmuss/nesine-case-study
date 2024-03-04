package com.nesine.casestudy.common

sealed class UIResult<out T>{
    data class Success<T>(val data: T) : UIResult<T>()
    data class Failure<T>(val message: String) : UIResult<T>()
}
