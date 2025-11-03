package com.example.footballscoreapp.data.remote

import com.example.footballscoreapp.domain.entities.LoadingException
import com.example.footballscoreapp.utils.myLog
import retrofit2.HttpException
import java.io.IOException

fun Throwable.parseToLoadingException(): LoadingException {
    myLog(this.stackTraceToString())
    return when (this) {
        is HttpException -> LoadingException.HttpError(this)

        is IOException -> LoadingException.NetworkError(this)

        is LoadingException -> this

        else -> LoadingException.OtherError(this)
    }
}