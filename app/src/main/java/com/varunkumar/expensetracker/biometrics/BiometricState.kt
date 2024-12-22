package com.varunkumar.expensetracker.biometrics

data class BiometricState(
    val uiState: BiometricUiState = BiometricUiState.Success, // set to initial
    val message: String = ""
)