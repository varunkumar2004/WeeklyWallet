package com.varunkumar.expensetracker.sms

data class ModelExpenseResult(
    val amount: Double,
    val expenseType: String,
    val name: String
)