package com.varunkumar.expensetracker.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AllInclusive
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.FoodBank
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.varunkumar.expensetracker.data.CurrencyType
import com.varunkumar.expensetracker.data.ExpenseType
import com.varunkumar.expensetracker.home.HomeState
import java.time.format.DateTimeFormatter

@Composable
fun DailyExpenseHistoryContainer(
    modifier: Modifier = Modifier,
    homeState: HomeState
) {
    val expenseTypes = listOf(
        ExpenseType.ALL,
        ExpenseType.FOOD,
        ExpenseType.ENTERTAINMENT,
        ExpenseType.FINANCE
    )

    var selectedExpenseType by remember {
        mutableStateOf(expenseTypes[0])
    }

    var isDropDownMenuOpen by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Outlined.History,
                    contentDescription = null
                )

                Column {
                    Text(
                        text = "History",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = homeState.selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedContent(targetState = selectedExpenseType) {
                    Button(
                        onClick = { isDropDownMenuOpen = true }
                    ) {
                        Text(text = it.name)
                    }
                }

                DropdownMenu(
                    expanded = isDropDownMenuOpen,
                    onDismissRequest = {
                        isDropDownMenuOpen = false
                    }
                ) {
                    expenseTypes.forEach { expenseType ->
                        DropdownMenuItem(
                            text = {
                                Text(text = expenseType.name)
                            },
                            onClick = {
                                selectedExpenseType = expenseType
                                isDropDownMenuOpen = false
                            }
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (homeState.dateSpecificExpenses.isEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "No expenses today.",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                itemsIndexed(homeState.dateSpecificExpenses) { index, item ->
                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent
                        ),
                        leadingContent = {
                            TransactionSymbol(
                                transactionType = ExpenseType.ENTERTAINMENT
                            )
                        },
                        supportingContent = {
                            Text(
                                text = "at 2:20PM",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        headlineContent = {
                            Text(
                                text = "Netflix",
                                color = MaterialTheme.colorScheme.tertiary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        trailingContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.secondary,
                                    imageVector = Icons.Outlined.CurrencyRupee,
                                    contentDescription = null
                                )

                                Text(
                                    text = item.amount.toString(),
                                    color = MaterialTheme.colorScheme.secondary,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencySymbol(
    currencyType: CurrencyType
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (currencyType) {
                CurrencyType.RUPEE -> "₹"
                CurrencyType.DOLLAR -> "₹"
                CurrencyType.POUND -> "₹"
            },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun TransactionSymbol(
    transactionType: ExpenseType
) {
    Icon(
        imageVector = when (transactionType) {
            ExpenseType.ENTERTAINMENT -> Icons.Outlined.LocalMovies

            ExpenseType.FINANCE -> Icons.Outlined.Money

            ExpenseType.FOOD -> Icons.Outlined.FoodBank

            ExpenseType.ALL -> Icons.Outlined.AllInclusive

            else -> Icons.Outlined.AllInclusive
        },
        tint = MaterialTheme.colorScheme.tertiary,
        contentDescription = null
    )
}