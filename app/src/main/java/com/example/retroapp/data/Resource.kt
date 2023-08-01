package com.example.retroapp.data

import java.lang.Exception

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception, var hasBeenHandled: Boolean = false) :
        Resource<Nothing>()

    object Loading : Resource<Nothing>()
}
