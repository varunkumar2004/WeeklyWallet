package com.varunkumar.expensetracker.ui.components

import kotlinx.datetime.DayOfWeek
import java.time.LocalDate

fun isFirstDateOfCurrentWeek(
    currentDate: LocalDate,
    selectedDate: LocalDate
): Boolean {
    val startOfWeek = currentDate.with(DayOfWeek.MONDAY)
    return selectedDate == startOfWeek
}