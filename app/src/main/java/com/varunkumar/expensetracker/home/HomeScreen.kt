package com.varunkumar.expensetracker.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.varunkumar.expensetracker.home.components.DailyExpenseHistoryContainer
import com.varunkumar.expensetracker.home.components.WeeklyExpenseContainer

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val weightModifier = Modifier
            .fillMaxWidth()
            .weight(0.5f)

        WeeklyExpenseContainer(
            modifier = weightModifier,
            homeState = state,
            onDayClick = homeViewModel::selectDayItem
        )

        DailyExpenseHistoryContainer(
            modifier = weightModifier,
            homeState = state
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    HomeScreen(
        modifier = Modifier.fillMaxSize()
    )
}