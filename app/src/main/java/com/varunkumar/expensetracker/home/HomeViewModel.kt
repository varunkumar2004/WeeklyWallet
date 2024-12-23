package com.varunkumar.expensetracker.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.expensetracker.DataStoreRepository
import com.varunkumar.expensetracker.UiState
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

    var isBottomDrawerOpen = MutableStateFlow(false)
        private set

    var isCalendarAlertOpen = MutableStateFlow(false)
        private set

    fun openDailyLimitAlert() {
        isDailyLimitAlertOpen.update { true }
    }

    fun openCalendarAlert() {
        isCalendarAlertOpen.update { true }
    }

    fun openBottomSheet() {
        isBottomDrawerOpen.update { true }
    }

    fun closeDailyLimitAlert() {
        isDailyLimitAlertOpen.update { false }
    }

    fun closeCalenderAlert() {
        isCalendarAlertOpen.update { false }
    }

    fun closeBottomSheet() {
        isBottomDrawerOpen.update { false }
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
            _state.update { it.copy(uiState = UiState.Loading) }

            try {
                expenseDao.insertExpense(expense)

                _state.update {
                    it.copy(
                        uiState = UiState.Success(
                            "expense of amount: ${expense.amount} added"
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        uiState = UiState.Error(
                            e.localizedMessage ?: "Some Error Occurred"
                        )
                    )
                }
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            try {
                expenseDao.deleteExpense(expense)

                _state.update {
                    it.copy(
                        uiState = UiState.Success(
                            "expense of amount: ${expense.name} deleted"
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        uiState = UiState.Error(
                            e.localizedMessage ?: "Some Error Occurred"
                        )
                    )
                }
            }
        }
    }
}

