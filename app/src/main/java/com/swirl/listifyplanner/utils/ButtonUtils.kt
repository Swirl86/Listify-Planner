package com.swirl.listifyplanner.utils

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun getButtonElevation() = ButtonDefaults.buttonElevation(
    defaultElevation = 2.dp,
    pressedElevation = 8.dp,
    disabledElevation = 0.dp,
    hoveredElevation = 4.dp,
    focusedElevation = 4.dp
)