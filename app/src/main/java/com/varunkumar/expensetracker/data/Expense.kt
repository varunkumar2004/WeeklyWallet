package com.varunkumar.expensetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val year: Int = LocalDateTime.now().year,
    val monthOfYear: Int = LocalDateTime.now().monthValue,
    val dayOfMonth: Int = LocalDateTime.now().dayOfMonth,
    val time: Long = System.currentTimeMillis(),
    val name: String,
    val amount: Double,
    val expenseType: ExpenseType
)
