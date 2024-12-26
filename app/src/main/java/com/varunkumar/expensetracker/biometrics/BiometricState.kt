package com.varunkumar.expensetracker.biometrics

import com.varunkumar.expensetracker.UiState

data class BiometricState(
    val uiState: UiState = UiState.Success(), // set to initial
    val isSmsPermissionGranted: Boolean = false
)