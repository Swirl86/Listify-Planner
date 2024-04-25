package com.swirl.listifyplanner.presentation.calendar_screen.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.swirl.listifyplanner.utils.extenstions.toMillis
import com.swirl.listifyplanner.utils.extenstions.yearRange
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker() {
    val dateTime = LocalDateTime.now()

    val datePickerState = remember {
        DatePickerState(
            yearRange = dateTime.yearRange(),
            initialDisplayedMonthMillis = dateTime.toMillis(),
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = null
        )
    }

    DatePicker(state = datePickerState)
}

@Preview(showBackground = true)
@Composable
fun MyDatePickerPreview() {
    MyDatePicker()
}