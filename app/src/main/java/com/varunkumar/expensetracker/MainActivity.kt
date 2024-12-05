package com.varunkumar.expensetracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.varunkumar.expensetracker.biometrics.BiometricUiState
import com.varunkumar.expensetracker.biometrics.BiometricsScreen
import com.varunkumar.expensetracker.biometrics.BiometricsViewModel
import com.varunkumar.expensetracker.home.ExpensesScreen
import com.varunkumar.expensetracker.ui.components.ContainerView
import com.varunkumar.expensetracker.ui.components.Routes
import com.varunkumar.expensetracker.ui.theme.ExpenseTrackerTheme
import com.varunkumar.expensetracker.wallet.WalletScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val biometricViewModel = hiltViewModel<BiometricsViewModel>()
            val biometricState by biometricViewModel.state.collectAsState()
            val navController = rememberNavController()
            var selectedRoute by remember {
                mutableStateOf<Routes>(Routes.Biometrics)
            }

            LaunchedEffect(selectedRoute) {
                navController.navigate(selectedRoute.route) {
                    when (selectedRoute) {
                        is Routes.Biometrics -> {
                            popUpTo(Routes.Biometrics.route) {
                                inclusive = true
                            }
                        }
                        else -> Unit
                    }
                }
            }

            ExpenseTrackerTheme {
                ContainerView(
                    route = selectedRoute,
                    onIconRouteClick = { selectedRoute = it }
                ) { modifier ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Biometrics.route
                    ) {
                        composable(Routes.Biometrics.route) {
                            val activity = LocalContext.current as FragmentActivity

                            BiometricsScreen(
                                modifier = modifier.fillMaxSize(),
                                message = when (biometricState.uiState) {
                                    is BiometricUiState.Success -> {
                                        selectedRoute = Routes.Home
                                        "Success"
                                    }

                                    else -> biometricState.message
                                },
                                biometricRequestClick = {
                                    biometricViewModel.biometricRequest(activity)
                                }
                            )
                        }

                        composable(Routes.Home.route) {
                            ExpensesScreen(
                                modifier = modifier.fillMaxSize()
                            )
                        }

                        composable(Routes.Wallet.route) {
                            WalletScreen(
                                modifier = modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}