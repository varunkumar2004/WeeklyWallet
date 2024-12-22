package com.varunkumar.expensetracker.biometrics

sealed interface BiometricUiState {
    data object Success : BiometricUiState
    data object Initial : BiometricUiState
    data object Error : BiometricUiState
}