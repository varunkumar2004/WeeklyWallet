package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.RunningWithErrors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.varunkumar.expensetracker.home.HomeState
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
        startDate = LocalDate.of(
            currentDate.year,
            currentDate.month,
            currentDate.dayOfMonth
        ),
        endDate = LocalDate.of(
            currentDate.year,
            currentDate.month,
            currentDate.dayOfMonth
        ),
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        DailyHeadingContainer(
            modifier = Modifier.fillMaxWidth(),
            totalExpense = totalExpense.toInt(),
            dailyLimit = homeState.dailyLimit,
            onDailyLimitTextButtonClick = onDailyLimitTextButtonClick
        )

        Box(
            modifier = modifier
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            GraphLinesContainer(
                modifier = modifier
                    .fillMaxHeight(),
                lineColor = MaterialTheme.colorScheme.primaryContainer,
                numberOfLines = 5
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

        DateContainer(
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
fun DailyHeadingContainer(
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
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleMedium,
                text = " / $dailyLimit"
            )

            Icon(
                modifier = Modifier
                    .size(15.dp),
                tint = MaterialTheme.colorScheme.tertiary,
                imageVector = Icons.Default.Edit,
                contentDescription = null
            )
        }
    }
}

@Composable
fun GraphLinesContainer(
    modifier: Modifier = Modifier,
    lineColor: Color,
    numberOfLines: Int
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
fun DateContainer(
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
                onClick = backButtonClick
            ) {
                Icon(
                    modifier = iconModifier,
                    imageVector = Icons.Default.ArrowBackIosNew,
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null
                )
            }

            Text(
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary,
                text = if (selectedDate == currentDate) "Today"
                else selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"))
            )

            IconButton(
                enabled = selectedDate < currentDate,
                onClick = nextButtonClick
            ) {
                Icon(
                    modifier = iconModifier,
                    imageVector = Icons.Default.ArrowForwardIos,
                    tint = MaterialTheme.colorScheme.tertiary,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun DailyExpenseItem(
    modifier: Modifier = Modifier,
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
            else itemColor.expenseItemColor
        )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = itemColor.dayColor,
            style = MaterialTheme.typography.bodySmall,
            text = weekDay.date.dayOfWeek.name
                .substring(0..2).lowercase().capitalize(Locale.ROOT)
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
        dayColor = MaterialTheme.colorScheme.tertiary,
        expenseItemColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
    )
}