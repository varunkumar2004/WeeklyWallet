package com.varunkumar.expensetracker.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.data.converters.ExpenseTypeConverters
import com.varunkumar.expensetracker.data.dao.ExpenseDao

@Database(entities = [Expense::class], version = 1)
@TypeConverters(ExpenseTypeConverters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
