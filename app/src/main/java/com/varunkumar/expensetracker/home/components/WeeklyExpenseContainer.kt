package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.RunningWithErrors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.varunkumar.expensetracker.home.HomeState
import java.time.LocalDate
import java.util.Locale

@Composable
fun WeeklyExpenseContainer(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    onCalendarTextButtonClick: () -> Unit,
    onDailyLimitTextButtonClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit
) {
    val currentDate = LocalDate.now()

    val totalExpense = if (homeState.dateSpecificExpenses.isNotEmpty())
        homeState.dateSpecificExpenses.sumOf { it.amount }.toFloat()
    else 0f

    val calendarState = rememberWeekCalendarState(
        startDate = LocalDate.of(currentDate.year, currentDate.month, 1),
        endDate = LocalDate.of(
            currentDate.year,
            currentDate.month,
            currentDate.dayOfMonth
        ),
    )

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onCalendarTextButtonClick
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = currentDate.month.name.lowercase().capitalize(Locale.ROOT)
                    )

                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Outlined.ArrowForwardIos,
                        contentDescription = null
                    )
                }
            }

            TextButton(
                onClick = onDailyLimitTextButtonClick
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(15.dp),
                        imageVector = Icons.Outlined.RunningWithErrors,
                        contentDescription = null
                    )

                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = homeState.dailyLimit.toString()
                    )
                }
            }
        }

        WeekCalendar(
            modifier = modifier.fillMaxHeight(),
            state = calendarState,
            userScrollEnabled = true,
            contentPadding = PaddingValues(horizontal = 16.dp),
            dayContent = { weekDay ->
                val isSelected = homeState.selectedDate == weekDay.date
                val itemColors =
                    if (isSelected) selectedDailyExpenseItem() else unSelectedDailyExpenseItem()

                val expenseContainerModifier = modifier
                    .fillMaxHeight()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(itemColors.backgroundColor)
                    .clickable { onDayClick(weekDay.date) }

                val dailySum = homeState.dailySumExpenses
                    .filter { it.dayOfMonth == weekDay.date.dayOfMonth }

                val totalDailySum = dailySum.sumOf { it.totalAmount }.toFloat()

                DailyExpenseItem(
                    modifier = expenseContainerModifier,
                    weekDay = weekDay,
                    itemColor = itemColors,
                    expense = if (isSelected) totalExpense
                    else totalDailySum,
                    dailyLimit = homeState.dailyLimit
                )
            }
        )
    }
}

@Composable
fun DailyExpenseItem(
    modifier: Modifier = Modifier,
    itemColor: DailyExpenseItemColor,
    weekDay: WeekDay,
    expense: Float,
    dailyLimit: Int
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        val expenseHeight = (expense / dailyLimit.toFloat())

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .zIndex(100f),
            textAlign = TextAlign.Center,
            color = if (expenseHeight < 1f)
                itemColor.dayColor else
                MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall,
            text = weekDay.date.dayOfMonth.toString()
        )

        val expenseModifier = Modifier
            .fillMaxHeight(expenseHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(
                if (expenseHeight >= 1f) MaterialTheme.colorScheme.error
                else itemColor.expenseItemColor
            )

        Box(
            modifier = expenseModifier
        )
    }
}

data class DailyExpenseItemColor(
    val backgroundColor: Color,
    val dayColor: Color,
    val expenseItemColor: Color
)

@Composable
fun selectedDailyExpenseItem(): DailyExpenseItemColor {
    return DailyExpenseItemColor(
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        dayColor = MaterialTheme.colorScheme.primary, // take care of the contrast of background,
        expenseItemColor = MaterialTheme.colorScheme.onSecondaryContainer
    )
}

@Composable
fun unSelectedDailyExpenseItem(): DailyExpenseItemColor {
    return DailyExpenseItemColor(
        backgroundColor = MaterialTheme.colorScheme.onPrimary,
        dayColor = MaterialTheme.colorScheme.onTertiaryContainer,
        expenseItemColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    )
}