package com.varunkumar.expensetracker.biometrics

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BiometricsViewModel @Inject constructor(
    private val biometricAuthentication: BiometricAuthentication
) : ViewModel() {
    private val _state = MutableStateFlow(BiometricState())
    val state = _state.asStateFlow()

    fun biometricRequest(activity: FragmentActivity) {
        biometricAuthentication.promptBiometricAuth(
            title = "Use your biometrics",
            negativeButtonText = "Cancel",
            fragmentActivity = activity,
            onSuccess = {
                _state.update { it.copy(uiState = BiometricUiState.Success) }
            },
            onFailed = {
                _state.update {
                    it.copy(
                        uiState = BiometricUiState.Error,
                        message = "Wrong biometrics"
                    )
                }
            },
            onError = { _, error ->
                _state.update { it.copy(uiState = BiometricUiState.Error, message = error) }
            }
        )
    }
}

data class BiometricState(
    val uiState: BiometricUiState = BiometricUiState.Initial, // set to initial
    val message: String = ""
)



