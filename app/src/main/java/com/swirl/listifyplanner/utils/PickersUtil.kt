package com.swirl.listifyplanner.utils

import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.runtime.Composable
import com.swirl.listifyplanner.ui.theme.Pink40
import com.swirl.listifyplanner.ui.theme.Pink80
import com.swirl.listifyplanner.ui.theme.Purple40
import com.swirl.listifyplanner.ui.theme.Purple700
import com.swirl.listifyplanner.ui.theme.Purple80
import com.swirl.listifyplanner.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getDatePickerColors() = DatePickerDefaults.colors(
    todayContentColor = Pink40,
    todayDateBorderColor = Purple700,
    selectedDayContentColor = Purple80,
    dayContentColor = Purple700,
    selectedDayContainerColor = Purple700,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getTimePickerColors() = TimePickerDefaults.colors(
    clockDialColor = Purple40,
    selectorColor = Pink80,
    containerColor = PurpleGrey80,
    clockDialUnselectedContentColor = Purple80,
)