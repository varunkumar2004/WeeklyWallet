package com.varunkumar.expensetracker.data.converters

import androidx.room.TypeConverter
import com.varunkumar.expensetracker.data.ExpenseType

class ExpenseTypeConverters {
    @TypeConverter
    fun fromExpenseType(value: ExpenseType): String {
        return value.name
    }

    @TypeConverter
    fun toExpenseType(value: String): ExpenseType {
        return ExpenseType.valueOf(value)
    }
}
