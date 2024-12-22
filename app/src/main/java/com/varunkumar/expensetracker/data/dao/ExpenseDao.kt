package com.varunkumar.expensetracker.data.dao

import androidx.room.*
import com.varunkumar.expensetracker.data.Expense

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expenses WHERE year = :year AND monthOfYear = :month AND dayOfMonth = :day ORDER BY time ASC")
    suspend fun getExpensesForDay(year: Int, month: Int, day: Int): List<Expense>

    @Query("SELECT dayOfMonth, SUM(amount) AS totalAmount FROM EXPENSES GROUP BY dayOfMonth HAVING year = :year AND monthOfYear = :month ORDER BY dayOfMonth ASC")
    suspend fun getExpensesSumForMonth(year: Int, month: Int): List<DailyExpenseSum>
//
//    @Query("SELECT * FROM expenses WHERE year = :year AND monthOfYear = :month ORDER BY dayOfMonth ASC, time ASC")
//    suspend fun getExpensesForMonth(year: Int, month: Int): List<Expense>
//
//    @Query("SELECT * FROM expenses WHERE year = :year ORDER BY monthOfYear ASC, dayOfMonth ASC, time ASC")
//    suspend fun getExpensesForYear(year: Int): List<Expense>
//
//    @Query("SELECT * FROM expenses ORDER BY time DESC")
//    suspend fun getAllExpenses(): List<Expense>
}
