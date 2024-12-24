package com.varunkumar.expensetracker

sealed interface UiState {
    data object Initial : UiState
    data object Loading : UiState
    data class Success(val outputText: String? = null) : UiState
    data class Error(val errorMessage: String) : UiState
}