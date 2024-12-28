package com.varunkumar.expensetracker.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.varunkumar.expensetracker.data.CurrencyType
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.home.HomeState
import com.varunkumar.expensetracker.ui.components.extractTimeFromLong
import java.time.LocalDate

@Composable
fun DailyExpenseHistoryContainer(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    openAddExpenseSheet: () -> Unit,
    onDeleteIconButtonClick: (Expense) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        AnimatedVisibility(visible = homeState.selectedDate == LocalDate.now()) {
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

                    Text(
                        text = "History",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Button(onClick = openAddExpenseSheet) {
                    Text(text = "Add Expense")
                }
            }
        }

        AnimatedVisibility(visible = homeState.dateSpecificExpenses.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(homeState.dateSpecificExpenses) { item ->
                    DailyExpenseGridItem(
                        item = item,
                        onDeleteIconButtonClick = { onDeleteIconButtonClick(item) }
                    )
                }
            }
        }

        AnimatedVisibility(visible = homeState.dateSpecificExpenses.isEmpty()) {
            ListItem(
                modifier = modifier,
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                ),
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary)
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = Icons.Default.BubbleChart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiary,
                        )
                    }
                },
                headlineContent = {
                    Text(
                        text = "No Expenses",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            )
        }
    }
}

@Composable
fun DailyExpenseGridItem(
    modifier: Modifier = Modifier,
    item: Expense,
    onDeleteIconButtonClick: () -> Unit
) {
    ListItem(
        modifier = modifier,
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent
        ),
        leadingContent = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = item.expenseType.icon,
                    tint = MaterialTheme.colorScheme.onTertiary,
                    contentDescription = null
                )
            }

        },
        supportingContent = {
            Text(
                text = "${item.name} - ${extractTimeFromLong(item.time).lowercase()}",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        headlineContent = {
            Text(
                text = "₹ ${item.amount}",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        },
        trailingContent = {
            IconButton(onClick = onDeleteIconButtonClick) {
                Icon(
                    tint = MaterialTheme.colorScheme.secondary,
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null
                )
            }
        }
    )
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