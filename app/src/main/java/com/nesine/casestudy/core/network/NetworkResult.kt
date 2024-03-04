package com.nesine.casestudy.core.network

import java.lang.Exception

sealed class NetworkResult<out R>{

    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Failure(val error:Error) : NetworkResult<Nothing>()
    class GeneralFailure(val e: Exception): NetworkResult<Nothing>()
}