package com.example.footballscoreapp.domain.entities

sealed class LoadingException : Throwable() {

    data object NetworkError : LoadingException()

    data object HttpError : LoadingException()

    data object OtherError : LoadingException()
}