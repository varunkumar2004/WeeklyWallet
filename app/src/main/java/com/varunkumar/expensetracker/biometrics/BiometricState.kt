package com.varunkumar.expensetracker.biometrics

data class BiometricState(
    val uiState: BiometricUiState = BiometricUiState.Initial, // set to initial
    val isSmsPermissionGranted: Boolean = false,
    val message: String = ""
)