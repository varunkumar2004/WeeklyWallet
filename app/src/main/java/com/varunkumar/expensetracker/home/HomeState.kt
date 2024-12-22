package com.varunkumar.expensetracker.home

import com.varunkumar.expensetracker.UiState
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.data.dao.DailyExpenseSum
import java.time.LocalDate

data class HomeState(
    val uiState: UiState = UiState.Initial,
    val dailyLimit: Int = 500,
    val selectedDate: LocalDate = LocalDate.now(),
    val dailySumExpenses: List<DailyExpenseSum> = emptyList(),
    val dateSpecificExpenses: List<Expense> = emptyList()
)
