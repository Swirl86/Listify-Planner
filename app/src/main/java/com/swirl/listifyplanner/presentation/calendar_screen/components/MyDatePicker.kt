package com.swirl.listifyplanner.presentation.calendar_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.utils.extenstions.*
import com.swirl.listifyplanner.utils.getDatePickerColors
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

    val selectedDate  = datePickerState.selectedDateMillis?.convertMillisToLocalDate()
    val dateToString = selectedDate?.dateToString() ?: "Choose Date"
    var showDialog by remember { mutableStateOf(false) }

    val colors = getDatePickerColors()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .clickable(onClick = {
                    showDialog = true
                }),
            text = dateToString,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )
        if (showDialog) {
            DatePickerDialog(
                shape = RoundedCornerShape(6.dp),
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.padding(8.dp).weight(1f),
                    colors = colors,
                    showModeToggle = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyDatePickerPreview() {
    MyDatePicker()
}