package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kizitonwose.calendar.compose.VerticalCalendar

@Composable
fun CalendarAlert(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Text(
                text = "Calendar",
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column {
                VerticalCalendar(
                    dayContent = {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = it.date.dayOfMonth.toString())
                        }
                    }
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {

                }
            ) {
                Text(text = "Confirm")
            }
        }
    )
}