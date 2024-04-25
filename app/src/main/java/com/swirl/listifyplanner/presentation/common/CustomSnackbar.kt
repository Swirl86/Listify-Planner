package com.swirl.listifyplanner.presentation.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun snackbar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    onAction: () -> Unit
) {
    scope.launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        val snackbarResult = snackbarHostState.showSnackbar(
            message,
            actionLabel,
            duration = SnackbarDuration.Short
        )

        when(snackbarResult) {
            SnackbarResult.ActionPerformed -> {
                onAction()
            }
            SnackbarResult.Dismissed -> {
                // DISMISS
            }
        }
    }
}