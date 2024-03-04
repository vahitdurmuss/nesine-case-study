package com.nesine.casestudy.common

import java.lang.Exception

sealed class UIResult<out R>{

    data class Success<out T>(val data: T) : UIResult<T>()
    data class Failure(val error:Error) : UIResult<Nothing>()
    class GeneralFailure(val e: Exception): UIResult<Nothing>()
}
