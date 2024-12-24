package com.varunkumar.expensetracker.biometrics

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun RequestPermissions(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        if (isGranted.all { true }) onPermissionGranted()
        else onPermissionDenied()
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS
            )
        )
    }
}