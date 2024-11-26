package com.varunkumar.expensetracker.biometrics

interface BiometricUiState {
    object Success : BiometricUiState
    object Initial : BiometricUiState
    data class Error(val errorMessage: String) : BiometricUiState
}