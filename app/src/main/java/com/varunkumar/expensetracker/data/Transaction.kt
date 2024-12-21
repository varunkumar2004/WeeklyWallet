package com.varunkumar.expensetracker.data

data class Transaction(
    val time: Long,
    val name: String,
    val amount: Double,
    val label: String,
    val currency: String
)
