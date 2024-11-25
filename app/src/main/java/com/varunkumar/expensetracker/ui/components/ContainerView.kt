package com.varunkumar.expensetracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContainerView(
    modifier: Modifier = Modifier,
    showTitleBar: Boolean = true,
    showBottomBar: Boolean = true,
    view: @Composable () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            view()
        }
    }
}