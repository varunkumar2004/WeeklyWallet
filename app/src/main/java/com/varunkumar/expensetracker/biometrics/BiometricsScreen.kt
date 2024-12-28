package com.varunkumar.expensetracker.biometrics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun BiometricsScreen(
    modifier: Modifier = Modifier,
    message: String,
    biometricRequestClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        biometricRequestClick()
    }

    Box(modifier = modifier) {
        Box(
            modifier = modifier
                .zIndex(100f)
//                .blur(1.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
        )

        Column(
            modifier = modifier
                .blur(100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {



            Spacer(modifier = Modifier.height(10.dp))

            Text(text = message)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BiometricPrev() {
    BiometricsScreen(
        modifier = Modifier.fillMaxSize(),
        message = "asdkhf,",
        biometricRequestClick = {}
    )
}

