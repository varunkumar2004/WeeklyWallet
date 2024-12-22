package com.varunkumar.expensetracker.home

import com.varunkumar.expensetracker.UiState
import com.varunkumar.expensetracker.data.Expense
import java.time.LocalDate

data class HomeState(
    val uiState: UiState = UiState.Initial,
    val dailyLimit: Int? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val dateSpecificExpenses: List<Expense> = emptyList()
)
