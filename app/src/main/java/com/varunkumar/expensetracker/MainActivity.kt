package com.varunkumar.expensetracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varunkumar.expensetracker.biometrics.BiometricAuthentication
import com.varunkumar.expensetracker.biometrics.BiometricsScreen
import com.varunkumar.expensetracker.biometrics.BiometricsViewModel
import com.varunkumar.expensetracker.home.HomeScreen
import com.varunkumar.expensetracker.ui.components.ContainerView
import com.varunkumar.expensetracker.ui.components.Routes
import com.varunkumar.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val biometricViewModel = hiltViewModel<BiometricsViewModel>()
            val biometricAuthenticator = BiometricAuthentication(this)
            val activity = LocalContext.current as FragmentActivity
            val navController = rememberNavController()
            val selectedRoute by biometricViewModel.navRoute.collectAsStateWithLifecycle()

            var biometricMessage by remember {
                mutableStateOf("")
            }

            ExpenseTrackerTheme {
                ContainerView(
                    route = selectedRoute,
                    onIconRouteClick = { selectedRoute = it }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Biometrics.route
                    ) {
                        composable(Routes.Biometrics.route) {
                            BiometricsScreen(
                                modifier = Modifier.fillMaxSize(),
                                message = biometricMessage,
                                biometricRequestClick = {
                                    biometricAuthenticator.promptBiometricAuth(
                                        title = "Login",
                                        subtitle = "Use your biometrics to login",
                                        negativeButtonText = "Cancel",
                                        fragmentActivity = activity,
                                        onSuccess = {
                                            navController.navigate("home") {
                                                popUpTo("biometrics") { inclusive = true }
                                            }
                                        },
                                        onFailed = {
                                            biometricMessage = "Wrong fingerprint or face id"
                                        },
                                        onError = { _, error ->
                                            biometricMessage = error
                                        }
                                    )
                                }
                            )
                        }

                        composable(Routes.Home.route) {
                            HomeScreen()
                        }
                    }
                }
            }
        }
    }
}