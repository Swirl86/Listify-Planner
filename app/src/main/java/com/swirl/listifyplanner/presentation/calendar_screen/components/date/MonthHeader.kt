package com.swirl.listifyplanner.presentation.calendar_screen.components.date

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord

@Composable
fun MonthHeader(monthTitle: String) {
    Text(
        text = monthTitle.capitalizeWord(),
        style =  MaterialTheme.typography.h6,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = Color.Black
    )
}