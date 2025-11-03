package com.example.footballscoreapp.presentation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.parseDateToStringHoursAndMinutes(): String {
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return outputFormat.format(this)
}

fun Date.parseDateToStringFullDate(): String {
    val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return outputFormat.format(this)
}

fun Date.parseDateToStringFullDateWithoutHours(): String {
    val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return outputFormat.format(this)
}