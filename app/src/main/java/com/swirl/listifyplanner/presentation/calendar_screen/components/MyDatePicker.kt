package com.swirl.listifyplanner.presentation.calendar_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.common.TextIconButton
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.convertMillisToLocalDate
import com.swirl.listifyplanner.utils.extenstions.dateToString
import com.swirl.listifyplanner.utils.extenstions.toMillis
import com.swirl.listifyplanner.utils.extenstions.yearRange
import com.swirl.listifyplanner.utils.getDatePickerColors
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(onButtonClick: (String) -> Unit) {
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
    var showDialog by remember { mutableStateOf(false) }
    val colors = getDatePickerColors()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextIconButton(
            text =  UiText.StringResource(R.string.calendar_top_title).asString(),
            icon = Icons.Default.CalendarMonth,
            contentDescription = UiText.StringResource(R.string.icon_calendar).asString(),
            tint = TaskLightGreenBg,
            onClick = { showDialog = true }
        )
        if (showDialog) {
            DatePickerDialog(
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(6.dp),
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            selectedDate?.dateToString()?.let {
                                onButtonClick(it)
                            }
                        }
                    ) {
                        Text(text = UiText.StringResource(R.string.button_ok).asString())
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = UiText.StringResource(R.string.button_cancel).asString())
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier.weight(1f),
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
    MyDatePicker(onButtonClick = {
        println("Button clicked!")
    })
}