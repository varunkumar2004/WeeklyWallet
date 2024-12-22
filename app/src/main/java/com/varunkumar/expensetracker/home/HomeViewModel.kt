package com.varunkumar.expensetracker.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.expensetracker.DataStoreRepository
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.data.dao.ExpenseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val expenseDao: ExpenseDao
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())

//    init {
//        val expense = Expense(
//            name = "Netflix",
//            amount = 1000.toDouble(),
//            expenseType = ExpenseType.ENTERTAINMENT
//        )
//        viewModelScope.launch {
//            expenseDao.insertExpense(expense)
//        }
//    }

    val state = combine(_state, dataStoreRepository.dailyLimitFlow) { state, limit ->
        _state.update {
            it.copy(
                dailyLimit = limit,
                dailySumExpenses = expenseDao.getExpensesSumForMonth(
                    state.selectedDate.year,
                    state.selectedDate.monthValue
                ),
                dateSpecificExpenses = expenseDao.getExpensesForDay(
                    state.selectedDate.year,
                    state.selectedDate.monthValue,
                    state.selectedDate.dayOfMonth
                )
            )
        }
        Log.d("state update", state.toString())
        state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    var isDailyLimitAlertOpen = MutableStateFlow(false)
        private set

    fun openDailyLimitAlert() {
        isDailyLimitAlertOpen.update { true }
    }

    fun closeDailyLimitAlert() {
        isDailyLimitAlertOpen.update { false }
    }

    fun selectDayItem(newDate: LocalDate) {
        _state.update {
            it.copy(selectedDate = newDate)
        }
    }

    fun updateDailyLimit(limit: Int) {
        viewModelScope.launch {
            dataStoreRepository.updateDailyLimit(limit)
            closeDailyLimitAlert()
        }
    }


    // room database operations
    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.insertExpense(expense)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseDao.deleteExpense(expense)
        }
    }

    fun getExpensesForDay(
        localDate: LocalDate
    ) {
        viewModelScope.launch {
            val expenses = expenseDao.getExpensesForDay(
                localDate.year,
                localDate.monthValue,
                localDate.dayOfMonth
            )

            _state.update { it.copy(dateSpecificExpenses = expenses) }
            Log.d("expenses list", _state.toString())
        }
    }
}

