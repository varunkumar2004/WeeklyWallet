package com.varunkumar.expensetracker.data

data class Expense(
    val time: Long,
    val name: String,
    val amount: Double,
    val expenseType: ExpenseType
)
