package com.varunkumar.expensetracker.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import java.time.temporal.TemporalAdjusters

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WeeklyExpenseContainer(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    onDayClick: (LocalDate) -> Unit
) {
    val currentDate = LocalDate.now()
    val expense = 700f
    val lastDateOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth())

    var showAlert by remember { mutableStateOf(false) }
    val calendarState = rememberWeekCalendarState(
        startDate = LocalDate.of(currentDate.year, currentDate.month, 1),
        endDate = LocalDate.of(
            currentDate.year,
            currentDate.month,
            currentDate.dayOfMonth
        ),
    )

    if (showAlert) {
        DailyExpenseLimitAlert(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { showAlert = !showAlert },
            onConfirm = { showAlert = !showAlert }
        )
    }

    Column(
        modifier = modifier
    ) {
        TextButton(
            modifier = Modifier.padding(start = 16.dp),
            onClick = { /*TODO*/ }
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = currentDate.month.name.lowercase().capitalize()
                )

                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = Icons.Outlined.ArrowForwardIos,
                    contentDescription = null
                )
            }
        }

        WeekCalendar(
            modifier = modifier
                .fillMaxHeight(),
            state = calendarState,
            userScrollEnabled = true,
            contentPadding = PaddingValues(horizontal = 16.dp),
            dayContent = { weekDay ->
                val isSelected = homeState.selectedDay == weekDay.date
                val itemColors =
                    if (isSelected) selectedDailyExpenseItem() else unSelectedDailyExpenseItem()

                val expenseContainerModifier = modifier
                    .fillMaxHeight()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(itemColors.backgroundColor)
                    .clickable { onDayClick(weekDay.date) }

                AnimatedContent(
                    targetState = itemColors,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = 500)) with
                        fadeOut(animationSpec = tween(durationMillis = 500))
                    },
                    label = "Colors Animation"
                ) { colors ->
                    DailyExpenseItem(
                        modifier = expenseContainerModifier,
                        weekDay = weekDay,
                        itemColor = colors,
                        expense = expense
                    )
                }
            }
        )
    }
}

@Composable
fun DailyExpenseItem(
    modifier: Modifier = Modifier,
    itemColor: DailyExpenseItemColor,
    weekDay: WeekDay,
    expense: Float
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
                .zIndex(100f),
            textAlign = TextAlign.Center,
            color = itemColor.dayColor,
            style = MaterialTheme.typography.bodySmall,
            text = weekDay.date.dayOfMonth.toString()
        )

        val expenseHeight = (expense / 1000.0).toFloat()
        val expenseModifier = Modifier
            .fillMaxHeight(expenseHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(itemColor.expenseItemColor)

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
        dayColor = MaterialTheme.colorScheme.tertiary, // take care of the contrast of background
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