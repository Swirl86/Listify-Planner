package com.swirl.listifyplanner.presentation.calendar_screen.components.date.picker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.swirl.listifyplanner.utils.getTimePickerColors
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePicker() {
    val dateTime = LocalDateTime.now()

    val timePickerState = remember {
        TimePickerState(
            is24Hour = true,
            initialHour = dateTime.hour,
            initialMinute = dateTime.minute
        )
    }

    val colors = getTimePickerColors()

    TimePicker(state = timePickerState, colors = colors)
}

@Preview(showBackground = true)
@Composable
fun MyTimePickerPreview() {
    MyTimePicker()
}