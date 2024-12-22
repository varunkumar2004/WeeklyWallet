package com.varunkumar.expensetracker.ui.components

import java.text.SimpleDateFormat

fun extractTimeFromLong(date: Long): String {
    val simpleDateFormat = SimpleDateFormat("hh:mm a")
    return simpleDateFormat.format(date)
}