package com.nesine.casestudy.common

sealed class UIResult<out T>{
    data class Success<T>(val data: T) : UIResult<T>()
    data class Failure(val message: String) : UIResult<Nothing>()
}
