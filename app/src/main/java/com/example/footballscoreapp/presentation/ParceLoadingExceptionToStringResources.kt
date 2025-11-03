package com.example.footballscoreapp.presentation

import com.example.footballscoreapp.R
import com.example.footballscoreapp.domain.entities.LoadingException


fun LoadingException.parseLoadingExceptionToStringResource() = when (this) {
    is LoadingException.HttpError -> R.string.http_error
    is LoadingException.NetworkError -> R.string.network_error
    is LoadingException.OtherError -> R.string.other_error
}