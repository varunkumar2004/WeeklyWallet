package com.varunkumar.expensetracker

import android.os.Bundle
import android.widget.Toast
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
import com.varunkumar.expensetracker.biometrics.BiometricsScreen
import com.varunkumar.expensetracker.biometrics.BiometricsViewModel
import com.varunkumar.expensetracker.biometrics.RequestPermissions
import com.varunkumar.expensetracker.home.HomeScreen
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

        // TODO implement notifications permission request
        setContent {
            val biometricViewModel = hiltViewModel<BiometricsViewModel>()
            val activity = LocalContext.current as FragmentActivity
            val navController = rememberNavController()

            var selectedRoute by remember { mutableStateOf<Routes>(Routes.Home) }

            LaunchedEffect(Unit) {
                biometricViewModel.biometricRequest(activity)
            }

//            LaunchedEffect(selectedRoute) {
//                navController.navigate(selectedRoute.route) {
//                    popUpTo(Routes.Biometrics.route) {
//                        inclusive = true
//                    }
//                }
//            }

            ExpenseTrackerTheme {
                RequestPermissions(
                    onPermissionGranted = {
                        biometricViewModel.updatePermissionsGrantedStatus(true)
                    },
                    onPermissionDenied = {
                        Toast.makeText(
                            activity,
                            "Permissions denied. Unable to access SMS.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )

                ContainerView(
                    route = selectedRoute,
                    onIconRouteClick = { selectedRoute = it }
                ) { modifier ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.Home.route
                    ) {
//                        composable(Routes.Biometrics.route) {
//                            BiometricsScreen(
//                                modifier = modifier.fillMaxSize(),
//                                message = when (biometricState.uiState) {
//                                    is UiState.Success -> {
//                                        // consider permission request
//                                        selectedRoute = Routes.Home
//                                        "Success"
//                                    }
//
//                                    is UiState.Error -> {
//                                        (biometricState.uiState as UiState.Error).errorMessage
//                                    }
//
//                                    else -> ""
//                                },
//                                biometricRequestClick = {
//                                    biometricViewModel.biometricRequest(activity)
//                                }
//                            )
//                        }

                        composable(Routes.Home.route) {
                            val biometricState by biometricViewModel.state.collectAsState()

                            HomeScreen(
                                modifier = modifier.fillMaxSize(),
                                onBiometricRequestClick = {
                                    biometricViewModel.biometricRequest(
                                        activity
                                    )
                                },
                                isBiometricSuccess = when (biometricState.uiState) {
                                    is UiState.Success -> {
                                        selectedRoute = Routes.Home
                                        true
                                    }

                                    else -> false
                                }
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