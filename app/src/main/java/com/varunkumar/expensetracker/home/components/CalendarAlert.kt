package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.varunkumar.expensetracker.home.HomeState
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.util.Locale
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarAlert(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    onDismissRequest: () -> Unit
) {
    var selectedLocalDate: LocalDate
    var dayExpense = 0.toDouble()
    val calendarState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    LaunchedEffect(calendarState) {
        selectedLocalDate =
            LocalDate.ofEpochDay(TimeUnit.MILLISECONDS.toDays(calendarState.selectedDateMillis!!))
        dayExpense = homeState.dateSpecificExpenses.filter {
            it.dayOfMonth == selectedLocalDate.dayOfMonth &&
            it.monthOfYear == selectedLocalDate.month.value &&
            it.year == selectedLocalDate.year
        }.sumOf { it.amount }
    }

    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        content = {
            Column {
                DatePicker(
                    modifier = Modifier.fillMaxWidth(),
                    showModeToggle = false,
                    state = calendarState
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = dayExpense.toString()
                    )

                    Button(
                        onClick = onDismissRequest
                    ) {
                        Text(text = "Done")
                    }
                }
            }
        }
    )
}
