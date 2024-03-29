package com.sixlogs.pt.data.remoteRepo

import okhttp3.ResponseBody
import java.lang.Exception

sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
        ): Resource<Nothing>()


}