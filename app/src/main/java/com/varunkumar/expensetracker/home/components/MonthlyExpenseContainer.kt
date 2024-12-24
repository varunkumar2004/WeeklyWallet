package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.OutDateStyle

@Composable
fun MonthlyExpenseContainer(
    modifier: Modifier = Modifier
) {
    val calendarState = rememberCalendarState()

    HorizontalCalendar(
        modifier = modifier
            .fillMaxHeight(),
        state = calendarState,
        dayContent = { day ->
            DayExpenseItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, Color.LightGray)
                    .padding(5.dp),
                calendarDay = day
            )
        }
    )
}

@Composable
fun DayExpenseItem(
    modifier: Modifier = Modifier,
    calendarDay: CalendarDay
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = calendarDay.date.dayOfMonth.toString()
        )

        Text(
            modifier = Modifier.align(Alignment.BottomEnd),
            style = MaterialTheme.typography.bodySmall,
            text = "57%"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpensePrev() {
    MonthlyExpenseContainer(
        modifier = Modifier.fillMaxWidth()
    )
}