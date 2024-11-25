package com.varunkumar.expensetracker.biometrics

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.fragment.app.FragmentActivity

class BiometricAuthentication(
    private val context: Context
) {
    private lateinit var promptInfo: PromptInfo
    private lateinit var biometricPrompt: BiometricPrompt
    private val biometricManager = BiometricManager.from(context)

    private fun isBiometricAuthenticationAvailable(): BiometricAuthStatus {
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> BiometricAuthStatus.READY
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricAuthStatus.NOT_AVAILABLE
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricAuthStatus.TEMP_NOT_AVAILABLE
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricAuthStatus.AVAILABLE_BUT_NOT_ENROLLED
            else -> BiometricAuthStatus.NOT_AVAILABLE
        }
    }

    fun promptBiometricAuth(
        title: String,
        subtitle: String,
        negativeButtonText: String,
        fragmentActivity: FragmentActivity,
        onSuccess: (result: BiometricPrompt.AuthenticationResult) -> Unit,
        onFailed: () -> Unit,
        onError: (errorCode: Int, errorString: String) -> Unit
    ) {
        when (isBiometricAuthenticationAvailable()) {
            BiometricAuthStatus.READY -> {
                biometricPrompt = BiometricPrompt(
                    fragmentActivity,
                    object: BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            onSuccess(result)
                        }

                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            onError(errorCode, errString.toString())
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            onFailed()
                        }
                    }
                )

                promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setSubtitle(subtitle)
                    .setNegativeButtonText(negativeButtonText)
                    .build()

                biometricPrompt.authenticate(promptInfo)
            }

            BiometricAuthStatus.NOT_AVAILABLE -> {
                onError(BiometricAuthStatus.NOT_AVAILABLE.id, "Not available for this device.")
                return
            }

            BiometricAuthStatus.TEMP_NOT_AVAILABLE -> {
                onError(
                    BiometricAuthStatus.TEMP_NOT_AVAILABLE.id,
                    "Not available at this moment."
                )
                return
            }

            BiometricAuthStatus.AVAILABLE_BUT_NOT_ENROLLED -> {
                onError(
                    BiometricAuthStatus.AVAILABLE_BUT_NOT_ENROLLED.id,
                    "Biometrics not added."
                )
                return
            }
        }
    }
}