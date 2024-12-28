package com.varunkumar.expensetracker.biometrics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BiometricsScreen(
    modifier: Modifier = Modifier,
    message: String,
    biometricRequestClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = biometricRequestClick
        ) {
            Text(text = "Authenticate with Biometrics")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = message)
    }
}

