package com.nesine.casestudy.common

sealed class UIResult{
    data class Success<out T>(val data: T) : UIResult()
    data class Failure<out T>(val data: T) : UIResult()
}
