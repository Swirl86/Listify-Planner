package com.swirl.listifyplanner.presentation.calendar_screen.components.date.picker

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.convertMillisToLocalDate
import com.swirl.listifyplanner.utils.extenstions.toMillis
import com.swirl.listifyplanner.utils.extenstions.yearRange
import com.swirl.listifyplanner.utils.getDatePickerColors
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePicker(
    showDialog: MutableState<Boolean>,
    onPositiveClick: (LocalDate?) -> Unit
) {
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
    val colors = getDatePickerColors()

    DatePickerDialog(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(6.dp),
        onDismissRequest = { showDialog.value = false },
        confirmButton = {
            Button(
                onClick = {
                    showDialog.value = false
                    selectedDate?.let { onPositiveClick(it) }
                }
            ) {
                Text(text = UiText.StringResource(R.string.button_ok).asString())
            }
        },
        dismissButton = {
            Button(
                onClick = { showDialog.value = false }
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

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun MyDatePickerPreview() {
    MyDatePicker(
        showDialog = mutableStateOf(true),
        onPositiveClick = {
            println("onPositiveClick!")
        })
}