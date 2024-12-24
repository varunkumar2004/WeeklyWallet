package com.varunkumar.expensetracker.home.components

import android.widget.PopupMenu.OnDismissListener
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyExpenseLimitAlert(
    modifier: Modifier = Modifier,
    dailyLimit: Int,
    onDismissRequest: () -> Unit,
    onDailyLimitChange: (Int) -> Unit
) {
    var text by remember { mutableStateOf(dailyLimit.toString()) }

    AlertDialog(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Daily Limit",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    modifier = modifier,
                    placeholder = { Text(text = "Enter limit") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CurrencyRupee,
                            contentDescription = null
                        )
                    },
                    value = text,
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newText ->
                        if (newText.all { it.isDigit() } && newText.isNotEmpty()) {
                            text = newText
                        }
                        if (newText.isEmpty()) {
                            text = ""
                        }
                    }
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null
                    )

                    Text(text = "Please consider before changing this limit " +
                            "because your past expenses may be affected.")
                }
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onDailyLimitChange(
                        if (text.isEmpty()) 0
                        else text.toInt()
                    )
                }
            ) {
                Text(text = "Confirm")
            }
        }
    )
}