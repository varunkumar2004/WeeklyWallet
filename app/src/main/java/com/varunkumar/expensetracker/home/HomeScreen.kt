package com.varunkumar.expensetracker.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.home.components.AddExpenseSheet
import com.varunkumar.expensetracker.home.components.CalendarAlert
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
    val showCalendarAlert by homeViewModel.isCalendarAlertOpen.collectAsStateWithLifecycle()
    val showBottomDrawer by homeViewModel.isBottomDrawerOpen.collectAsStateWithLifecycle()

    if (showBottomDrawer) {
        ModalBottomSheet(
            onDismissRequest = homeViewModel::closeBottomSheet
        ) {
            AddExpenseSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp),
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
    }

    if (showDailyLimitAlert) {
        DailyExpenseLimitAlert(
            modifier = Modifier.fillMaxWidth(),
            dailyLimit = state.dailyLimit,
            onDismissRequest = homeViewModel::closeDailyLimitAlert,
            onDailyLimitChange = homeViewModel::updateDailyLimit
        )
    }

    if (showCalendarAlert) {
        CalendarAlert(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = homeViewModel::closeDailyLimitAlert
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
            onCalendarTextButtonClick = homeViewModel::openDailyLimitAlert,
            onDailyLimitTextButtonClick = homeViewModel::openDailyLimitAlert,
            onDayClick = homeViewModel::selectDayItem
        )

        DailyExpenseHistoryContainer(
            modifier = weightModifier,
            homeState = state,
            openAddExpenseSheet = homeViewModel::openBottomSheet
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