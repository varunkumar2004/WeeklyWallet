package com.varunkumar.expensetracker.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.varunkumar.expensetracker.data.CurrencyType
import com.varunkumar.expensetracker.data.Expense
import com.varunkumar.expensetracker.data.ExpenseType
import com.varunkumar.expensetracker.home.HomeState
import com.varunkumar.expensetracker.ui.components.extractTimeFromLong
import java.time.format.DateTimeFormatter

@Composable
fun DailyExpenseHistoryContainer(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    openAddExpenseSheet: () -> Unit
) {
    val totalAmount = homeState.dateSpecificExpenses.sumOf { it.amount }

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

            Button(onClick = openAddExpenseSheet) {
                Text(text = "Add Expense")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(homeState.dateSpecificExpenses) { index, it ->
                DailyExpenseGridItem(
                    item = it
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontWeight = FontWeight.SemiBold,
                    text = "Total Expense: $totalAmount",
                    color =
                    if (totalAmount < homeState.dailyLimit) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun DailyExpenseGridItem(
    modifier: Modifier = Modifier,
    item: Expense
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
                    modifier = Modifier.size(15.dp),
                    imageVector = item.expenseType.icon,
                    tint = MaterialTheme.colorScheme.onTertiary,
                    contentDescription = null
                )
            }

        },
        supportingContent = {
            Text(
                text = extractTimeFromLong(item.time),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        },
        headlineContent = {
            Text(
                text = item.name,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = item.amount.toString(),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    )

//    Column(
//        modifier = modifier
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.Top
//        ) {
//            Text(
//                text = item.name,
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Image(
//                modifier = Modifier
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.primaryContainer)
//                    .padding(10.dp),
//                imageVector = item.expenseType.icon,
//                contentDescription = null
//            )
//        }
//
//        Text(
//            text = item.amount.toString()
//        )
//
////                    Row {
////
////
////                        Image(
////                            imageVector = item.expenseType.icon,
////                            contentDescription = null
////                        )
////                    }
//    }
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