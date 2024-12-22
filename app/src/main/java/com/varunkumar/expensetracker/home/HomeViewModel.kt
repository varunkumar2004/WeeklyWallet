package com.varunkumar.expensetracker.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.expensetracker.DataStoreRepository
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
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = combine(_state, dataStoreRepository.dailyLimitFlow) { state, limit ->
        _state.update { it.copy(dailyLimit = limit) }
        state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun selectDayItem(newDate: LocalDate) {
        _state.update {
            it.copy(selectedDate = newDate)
        }
    }

    fun updateDailyLimit(limit: Int) {
        viewModelScope.launch {
            dataStoreRepository.updateDailyLimit(limit)
        }
    }
}

