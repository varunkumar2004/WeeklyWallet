package com.varunkumar.expensetracker

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.varunkumar.expensetracker.biometrics.BiometricAuthentication
import com.varunkumar.expensetracker.biometrics.BiometricsScreen
import com.varunkumar.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val biometricAuthenticator = BiometricAuthentication(this)
            val activity = LocalContext.current as FragmentActivity
            var biometricMessage by remember {
                mutableStateOf("")
            }

            ExpenseTrackerTheme {
                BiometricsScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = biometricMessage,
                    biometricRequestClick = {
                        biometricAuthenticator.promptBiometricAuth(
                            title = "Login",
                            subtitle = "Use your biometrics to login",
                            negativeButtonText = "Cancel",
                            fragmentActivity = activity,
                            onSuccess = { biometricMessage = "Success" },
                            onFailed = { biometricMessage = "Wrong fingerprint or face id" },
                            onError = { _, error ->
                                biometricMessage = error
                            }
                        )
                    }
                )
            }
        }
    }
}