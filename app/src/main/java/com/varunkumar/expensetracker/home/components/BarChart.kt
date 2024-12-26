package com.varunkumar.expensetracker.home.components

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun BarChartWithLines(
    data: List<Int>,
    modifier: Modifier = Modifier,
    maxBarHeight: Float = 200f,
    barWidth: Dp = 24.dp,
    barColor: Color = Color(0xFFB29DDB),
    lineColor: Color = Color.LightGray, // Color of the lines
    lineThickness: Dp = 1.dp,          // Thickness of the lines
    numberOfLines: Int = 4             // Number of grid lines
) {
    val maxValue = data.maxOrNull()?.toFloat() ?: 0f

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = modifier
        ) {
            val stepHeight = size.height / numberOfLines

            for (i in 1..numberOfLines) {
                drawLine(
                    color = Color.Red,
                    start = Offset(0f, i * stepHeight),
                    end = Offset(size.width, i * stepHeight),
                    strokeWidth = lineThickness.toPx()
                )
            }
        }

        Row(
            modifier = modifier
                .padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.forEach { value ->
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Box(modifier = Modifier.height(maxBarHeight.dp)) {

                        // Draw bars
                        Canvas(
                            modifier = Modifier
                                .zIndex(100f)
                                .size(barWidth, (maxBarHeight * (value / maxValue)).dp)
                                .align(Alignment.BottomCenter)
                        ) {
                            drawRoundRect(
                                color = barColor,
                                cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun BarChartWithLinesPreview() {
    val sampleData = listOf(10, 20, 40, 50, 15, 5, 25)

    BarChartWithLines(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        data = sampleData,
        maxBarHeight = 150f
    )
}
