package com.varunkumar.expensetracker.biometrics

interface BiometricUiState {
    object Success : BiometricUiState
    object Initial : BiometricUiState
    object Error : BiometricUiState
}