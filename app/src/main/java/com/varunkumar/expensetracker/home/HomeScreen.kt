package com.varunkumar.expensetracker.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import com.varunkumar.expensetracker.home.components.DailyExpenseLimitAlert
import com.varunkumar.expensetracker.home.components.ExpenseHistoryContainer

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PillExpenseContainer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        )

        Spacer(modifier = Modifier.height(10.dp))

        ExpenseHistoryContainer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        )
    }
}

@Composable
fun PillExpenseContainer(
    modifier: Modifier = Modifier
) {
    val filledIconButtonColors = IconButtonDefaults.filledIconButtonColors(
        containerColor = Color.LightGray,
        contentColor = Color.Black
    )

    var showAlert by remember { mutableStateOf(false) }

    val expenses = listOf(
        100,
        200,
        450,
        2340
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
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {}
            ) {
                Text(text = "December")
            }

            FilledIconButton(
                colors = filledIconButtonColors,
                onClick = { showAlert = true }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null
                )
            }
        }

        val fixedHeight = 300.dp

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 10.dp),
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(expenses) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color.Green)
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(40.dp))
                            .height(item.dp)
                            .background(Color.Red)
                    )
                }
            }
        }
    }

//    Card(
//        modifier = ,
//        elevation = CardDefaults.elevatedCardElevation(
//            defaultElevation = 10.dp
//        ),
//        colors = CardDefaults.elevatedCardColors(
//            containerColor = Color.Black
//        ),
//        onClick = { /*TODO*/ }
//    ) {
//        PillExpenseAnimation(
//            modifier = Modifier
//                .width(100.dp),
//            expense = expenses[0]
//        )
//    }
}

@Composable
private fun PillExpenseAnimation(
    modifier: Modifier = Modifier,
    pillHeight: Dp = 300.dp,
    expense: Int
) {
    Box(
        modifier = modifier
            .height(pillHeight)
            .clip(RoundedCornerShape(50.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.BottomEnd
    ) {
        Box(
            modifier = modifier
                .height(expense.dp)
                .background(Color.Red)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
    HomeScreen(
        modifier = Modifier.fillMaxSize()
    )
}