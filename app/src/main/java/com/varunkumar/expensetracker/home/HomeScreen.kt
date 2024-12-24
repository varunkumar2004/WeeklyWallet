package com.varunkumar.expensetracker.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.home.components.AddExpenseAlert
import com.varunkumar.expensetracker.home.components.DailyExpenseHistoryContainer
import com.varunkumar.expensetracker.home.components.DailyExpenseLimitAlert
import com.varunkumar.expensetracker.home.components.WeeklyExpenseContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val state by homeViewModel.state.collectAsStateWithLifecycle()
    val showDailyLimitAlert by homeViewModel.isDailyLimitAlertOpen.collectAsStateWithLifecycle()
    val showAddExpenseAlert by homeViewModel.isAddExpenseAlertOpen.collectAsStateWithLifecycle()

    if (showDailyLimitAlert) {
        DailyExpenseLimitAlert(
            modifier = Modifier.fillMaxWidth(),
            dailyLimit = state.dailyLimit,
            onDismissRequest = homeViewModel::closeDailyLimitAlert,
            onDailyLimitChange = homeViewModel::updateDailyLimit
        )
    }

    if (showAddExpenseAlert) {
        AddExpenseAlert(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            onConfirmClick = { expenseName, expenseAmount, expenseType ->
                val newExpense = Expense(
                    name = expenseName,
                    amount = expenseAmount,
                    expenseType = expenseType
                )

                homeViewModel.addExpense(newExpense)
                homeViewModel.closeBottomSheet()
            }
        )
    }

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
            onDailyLimitTextButtonClick = homeViewModel::openDailyLimitAlert,
            onDayClick = homeViewModel::selectDayItem
        )

        DailyExpenseHistoryContainer(
            modifier = weightModifier,
            homeState = state,
            openAddExpenseSheet = homeViewModel::openBottomSheet,
            onDeleteIconButtonClick = homeViewModel::deleteExpense
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