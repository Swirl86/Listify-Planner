package com.swirl.listifyplanner.presentation.calendar_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.swirl.listifyplanner.presentation.calendar_screen.components.MyDatePicker

@Composable
fun CalendarScreen() {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MyDatePicker()
    }

}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}