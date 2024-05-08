package com.swirl.listifyplanner.presentation.calendar_screen.components.date

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeekNumberHeader(weekNumber: Int) {
    Text(
        text = "Week $weekNumber",
        style =  MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = Color.Gray
    )
}