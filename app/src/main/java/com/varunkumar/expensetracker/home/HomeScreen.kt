package com.varunkumar.expensetracker.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.varunkumar.expensetracker.ui.components.ContainerView

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    ContainerView(
        modifier = modifier
    ) {
        Column {

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    HomeScreen(
        modifier = Modifier.fillMaxSize()
    )
}