package com.varunkumar.expensetracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerView(
    route: Routes,
    onIconRouteClick: (Routes) -> Unit,
    view: @Composable (Modifier) -> Unit
) {
    val showScaffoldElements = when (route) {
        Routes.Biometrics -> false
        else -> true
    }

    Scaffold(
        topBar = {
            if (showScaffoldElements) {
                CenterAlignedTopAppBar(
//                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                        containerColor = Color.Black,
//                        titleContentColor = Color.White
//                    ),
                    title = { Text(text = route.route.capitalize()) }
                )
            }
        },
        bottomBar = {
            if (showScaffoldElements) {
                NavigationBar {
                    NavigationBarItem(
                        selected = route == Routes.Home,
                        onClick = { onIconRouteClick(Routes.Home) },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Home,
                                contentDescription = null
                            )
                        }
                    )

                    NavigationBarItem(
                        selected = route == Routes.Wallet,
                        onClick = { onIconRouteClick(Routes.Wallet) },
                        icon = {
                            Icon(
                                imageVector = Icons.Outlined.Wallet,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        view(Modifier.padding(paddingValues))
    }
}