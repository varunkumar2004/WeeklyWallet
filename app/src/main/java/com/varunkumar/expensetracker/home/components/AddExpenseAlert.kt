package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.varunkumar.expensetracker.data.ExpenseType
import java.util.Locale

@Composable
fun AddExpenseAlert(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmClick: (String, Double, ExpenseType) -> Unit
) {
    var showExpenseTypeOptions by remember { mutableStateOf(false) }
    var expenseName by remember { mutableStateOf("") }
    var expenseAmount by remember { mutableStateOf("") }
    var expenseType by remember { mutableStateOf(ExpenseType.OTHER) }

    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Add Expense",
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp)),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = expenseName,
                    textStyle = TextStyle(fontSize = 20.sp),
                    colors = textFieldColors,
                    placeholder = { Text(text = "Enter expense name") },
                    onValueChange = { expenseName = it }
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors,
                    textStyle = TextStyle(fontSize = 20.sp),
                    value = expenseAmount,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CurrencyRupee,
                            contentDescription = null
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text(text = "Enter amount") },
                    onValueChange = { newText ->
                        if (newText.all { it.isDigit() } && newText.isNotEmpty()) {
                            expenseAmount = newText
                        }
                        if (newText.isEmpty()) {
                            expenseAmount = ""
                        }
                    }
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    DropdownMenu(
                        expanded = showExpenseTypeOptions,
                        onDismissRequest = { showExpenseTypeOptions = false }
                    ) {
                        ExpenseType.entries.forEach { type ->
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        expenseType = type
                                        showExpenseTypeOptions = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                text = type.name,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    OutlinedButton(onClick = { showExpenseTypeOptions = true }) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = expenseType.icon,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )

                            Text(
                                text = expenseType.name.lowercase().capitalize(Locale.ROOT)
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (expenseName.isNotEmpty() && expenseAmount.isNotEmpty()) {
                            onConfirmClick(
                                expenseName.trim(),
                                expenseAmount.toDouble(),
                                expenseType
                            )
                        }
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    )
}
