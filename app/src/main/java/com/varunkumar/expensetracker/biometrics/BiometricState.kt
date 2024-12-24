package com.varunkumar.expensetracker.biometrics

import com.varunkumar.expensetracker.UiState

data class BiometricState(
    val uiState: UiState = UiState.Initial, // set to initial
    val isSmsPermissionGranted: Boolean = false
)