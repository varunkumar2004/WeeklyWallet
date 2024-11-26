package com.varunkumar.expensetracker.biometrics

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.varunkumar.expensetracker.ui.components.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BiometricsViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<BiometricUiState>(BiometricUiState.Initial)
    private val _navRoute = MutableStateFlow<Routes>(Routes.Biometrics)
    val navRoute = _navRoute.asStateFlow()

    fun updateUiState(uiState: BiometricUiState) {
        _state.update { uiState }
    }

    fun updateNavRoute(route: Routes) {
        _navRoute.update { route }
    }
}




