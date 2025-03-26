package com.example.footballscoreapp.presentation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.parseDateToString(): String {
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return outputFormat.format(this)
}