package com.varunkumar.expensetracker.ui.components

sealed class Routes(val route: String) {
    data object Home: Routes(route = "home")
    data object Biometrics: Routes(route = "biometrics")
    data object Wallet: Routes(route = "wallet")
}