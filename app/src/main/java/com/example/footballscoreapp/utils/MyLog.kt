package com.example.footballscoreapp.utils

import android.util.Log
import com.example.footballscoreapp.BuildConfig

fun myLog(msg: String) {
    if (BuildConfig.DEBUG) {
        runCatching {
            Log.d("!!!", msg)
        }.getOrElse {
            println(msg)
        }
    }
}