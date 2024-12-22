package com.varunkumar.expensetracker.data

data class Expense(
    val time: Long,
    val name: String,
    val amount: Double,
    val transactionType: ExpenseType,
    val currencyType: CurrencyType = CurrencyType.RUPEE
)
