package com.varunkumar.expensetracker.home.components

import android.hardware.lights.Light
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.google.ai.client.generativeai.type.content
import kotlin.math.min

@Composable
fun ExpenseHistoryContainer(
    modifier: Modifier = Modifier
) {
    val expenses = listOf(
        100,
        200,
        450,
        2340
    )

    val pieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("SciFi", 65f, Color(0xFF333333)),
            PieChartData.Slice("Comedy", 35f, Color(0xFF666a86)),
            PieChartData.Slice("Drama", 10f, Color(0xFF95B8D1)),
            PieChartData.Slice("Romance", 40f, Color(0xFFF53844))
        ), plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = false,
        showSliceLabels = true
    )

    val expensesDescription = listOf(
        "2341234",
        "27340123",
        "2341234",
        "27340123",
        "2341234",
        "27340123"
    )

    Column(
        modifier = modifier
    ) {
//        PieChart(
//            modifier = Modifier
//                .size(200.dp),
//            pieChartData = pieChartData,
//            pieChartConfig = pieChartConfig
//        )


        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 5.dp,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(expensesDescription) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xffc1f7ad))
                        .padding(10.dp)
                ) {
                    Row {
                        Text(
                            text = "-$it",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(text = "₹")
                    }

                    Text(
                        style = MaterialTheme.typography.bodySmall,
                        text = "2: 45 pm"
                    )
                }


//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color(0xffc1f7ad))
//                        .aspectRatio(1 / 1f),
//                    contentAlignment = Alignment.TopEnd
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .size(40.dp)
//                            .border(1.dp, Color.LightGray)
//                    )
//
//
//                    Column(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(text = it)
//                    }
//                }

//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(1/1f)
//                        .background(Color.Gray)
//                        .drawWithContent {
//                            val trianglePath = androidx.compose.ui.graphics.Path().apply {
//                                // Define the triangle's vertices (adjust coordinates as needed)
//                                moveTo(10.dp.toPx(), 10.dp.toPx())
//                                lineTo(50.dp.toPx(), 50.dp.toPx())
//                                lineTo(10.dp.toPx(), 90.dp.toPx())
//                                close()
//                            }
//
//                            // Clip the content using the triangle path
//                            drawPath(trianglePath, color = Color.Red)
//                        }
//                ) {
//
//                }

//                Box(
//                    modifier = Modifier
//                        .size(150.dp)
//                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
//                ) {
//                    Canvas(modifier = Modifier.fillMaxWidth()) {
//                        val trianglePath = androidx.compose.ui.graphics.Path().apply {
//                            // Define the triangle coordinates for the cutout
//                            moveTo(size.width, 0f) // Top-right corner
//                            lineTo(size.width, size.height * 0.4f) // Mid-right
//                            lineTo(size.width * 0.6f, 0f) // Mid-top
//                            close()
//                        }
//
//                        // Clip the triangle cutout
//                        clipPath(trianglePath) {
//                            drawRect(
//                                color = Color.Gray, // Fill color
//                                size = Size(size.width, size.height),
////                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
//                            )
//                        }
//
//                        // Optionally, draw the background color again to simulate a cutout effect
//                        drawPath(
//                            path = trianglePath,
//                            color = Color.Red,
//                            style = Fill
//                        )
//                    }
//                }

//                ElevatedCard(
//                    modifier = Modifier,
//                    colors = CardDefaults.elevatedCardColors(
//                        containerColor = Color.Green
//                    ),
//                    shape = RoundedCornerShape(20.dp),
//                    onClick = { /*TODO*/ }
//                ) {
//
//
//
//                    Column(
//                        modifier = Modifier
//                            .padding(10.dp)
//                    ) {
//                        Text(text = it)
//                    }
//                }
            }
        }
//
//        LazyColumn(
//            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
//            verticalArrangement = Arrangement.spacedBy(2.dp)
//        ) {
//            items(expensesDescription) {
//                ExpenseHistoryItem(
//                    modifier = Modifier,
//                    history = it
//                )
//            }
//        }
    }
}