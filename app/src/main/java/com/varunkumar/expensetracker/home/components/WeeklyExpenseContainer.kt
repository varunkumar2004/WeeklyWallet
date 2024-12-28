package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.varunkumar.expensetracker.home.HomeState
import com.varunkumar.expensetracker.ui.components.isFirstDateOfCurrentWeek
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WeeklyExpenseContainer(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    onDateBackButtonClick: () -> Unit,
    onDateNextButtonClick: () -> Unit,
    onDailyLimitTextButtonClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit
) {
    val currentDate = LocalDate.now()
    val totalExpense = if (homeState.dateSpecificExpenses.isNotEmpty())
        homeState.dateSpecificExpenses.sumOf { it.amount }.toFloat()
    else 0f

    val calendarState = rememberWeekCalendarState(
        firstDayOfWeek = DayOfWeek.MONDAY,
        startDate = LocalDate.of(
            currentDate.year,
            currentDate.month,
            currentDate.dayOfMonth
        ),
        endDate = LocalDate.of(
            currentDate.year,
            currentDate.month,
            currentDate.dayOfMonth
        )
    )

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DailyHeadingContainer(
            modifier = Modifier.fillMaxWidth(),
            totalExpense = totalExpense.toInt(),
            dailyLimit = homeState.dailyLimit,
            onDailyLimitTextButtonClick = onDailyLimitTextButtonClick
        )

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            GraphLinesContainer(
                modifier = modifier
                    .fillMaxHeight()
            )

            WeekCalendar(
                modifier = modifier
                    .fillMaxHeight(),
                state = calendarState,
                userScrollEnabled = false,
                dayContent = { weekDay ->
                    val isSelected = homeState.selectedDate == weekDay.date
                    val dailySum =
                        homeState.dailySumExpenses.filter { it.dayOfMonth == weekDay.date.dayOfMonth }
                    val totalDailySum = dailySum.sumOf { it.totalAmount }.toFloat()
                    val itemColors =
                        if (isSelected) selectedDailyExpenseItem() else unSelectedDailyExpenseItem()

                    val expenseContainerModifier = modifier
                        .fillMaxHeight()

                    DailyExpenseItem(
                        modifier = expenseContainerModifier,
                        isSelected = isSelected,
                        weekDay = weekDay,
                        itemColor = itemColors,
                        expense = if (isSelected) totalExpense
                        else totalDailySum,
                        dailyLimit = homeState.dailyLimit,
                        onClick = { onDayClick(weekDay.date) }
                    )
                }
            )
        }

        DateCarouselContainer(
            modifier = Modifier
                .fillMaxWidth(),
            selectedDate = homeState.selectedDate,
            currentDate = currentDate,
            backButtonClick = onDateBackButtonClick,
            nextButtonClick = onDateNextButtonClick
        )
    }
}

@Composable
private fun DailyHeadingContainer(
    modifier: Modifier = Modifier,
    totalExpense: Int,
    dailyLimit: Int,
    onDailyLimitTextButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = totalExpense.toString(),
            style = MaterialTheme.typography.displayLarge
        )

        Row(
            modifier = Modifier.clickable { onDailyLimitTextButtonClick() },
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                text = " / $dailyLimit"
            )

            Icon(
                modifier = Modifier.size(15.dp),
                tint = MaterialTheme.colorScheme.secondary,
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun GraphLinesContainer(
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.onPrimary,
    numberOfLines: Int = 4
) {
    Canvas(
        modifier = modifier
    ) {
        val stepHeight = size.height / numberOfLines

        for (i in 1..numberOfLines) {
            drawLine(
                color = lineColor,
                start = Offset(0f, i * stepHeight),
                end = Offset(size.width, i * stepHeight),
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}

@Composable
private fun DateCarouselContainer(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    currentDate: LocalDate,
    backButtonClick: () -> Unit,
    nextButtonClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconModifier = Modifier
                .size(15.dp)

            IconButton(
                enabled = !isFirstDateOfCurrentWeek(
                    currentDate = currentDate,
                    selectedDate = selectedDate
                ),
                onClick = backButtonClick
            ) {
                Icon(
                    modifier = iconModifier,
                    imageVector = Icons.Default.ArrowBackIosNew,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            }

            Text(
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                text = if (selectedDate == currentDate) "Today"
                else "${
                    selectedDate.dayOfWeek.name.lowercase().capitalize(Locale.ROOT)
                }, ${selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"))}"
            )

            IconButton(
                enabled = selectedDate < currentDate,
                onClick = nextButtonClick
            ) {
                Icon(
                    modifier = iconModifier,
                    imageVector = Icons.Default.ArrowForwardIos,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun DailyExpenseItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    itemColor: DailyExpenseItemColor,
    weekDay: WeekDay,
    expense: Float,
    dailyLimit: Int,
    onClick: () -> Unit
) {
    val expenseHeight = (expense / dailyLimit.toFloat())

    val expenseModifier = Modifier
        .fillMaxHeight(expenseHeight)
        .fillMaxWidth()
        .clip(RoundedCornerShape(5.dp))
        .clickable { onClick() }
        .background(
            if (expenseHeight >= 1f) MaterialTheme.colorScheme.error
            else itemColor.itemColor
        )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = itemColor.dayColor,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            text = weekDay.date.dayOfWeek.name
                .substring(0..2).lowercase().capitalize(Locale.ROOT)
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxHeight()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = expenseModifier
            )
        }
    }
}

data class DailyExpenseItemColor(
    val dayColor: Color,
    val itemColor: Color
)

@Composable
fun selectedDailyExpenseItem(): DailyExpenseItemColor {
    return DailyExpenseItemColor(
        dayColor = MaterialTheme.colorScheme.tertiary,
        itemColor = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun unSelectedDailyExpenseItem(): DailyExpenseItemColor {
    return DailyExpenseItemColor(
        dayColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
        itemColor = MaterialTheme.colorScheme.primaryContainer
    )
}