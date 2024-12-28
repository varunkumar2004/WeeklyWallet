package com.varunkumar.expensetracker.biometrics

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.varunkumar.expensetracker.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BiometricsViewModel @Inject constructor(
    private val biometricAuthentication: BiometricAuthentication,
//    fragmentActivity: FragmentActivity
) : ViewModel() {
    private val _state = MutableStateFlow(BiometricState())
    val state = _state.asStateFlow()

    var showPermissionsAlert = MutableStateFlow(false)
        private set
//
//    init {
//        biometricRequest(fragmentActivity)
//    }

    fun biometricRequest(activity: FragmentActivity) {
        biometricAuthentication.promptBiometricAuth(
            title = "Use your biometrics",
            negativeButtonText = "Cancel",
            fragmentActivity = activity,
            onSuccess = {
                _state.update { it.copy(uiState = UiState.Success()) }
            },
            onFailed = {
                _state.update {
                    it.copy(
                        uiState = UiState.Error("Wrong biometrics")
                    )
                }
            },
            onError = { _, error ->
                _state.update {
                    it.copy(
                        uiState = UiState.Error(error)
                    )
                }
            }
        )
    }

    fun updatePermissionsGrantedStatus(isGranted: Boolean) {
        _state.update { it.copy(isSmsPermissionGranted = isGranted) }
    }
}



