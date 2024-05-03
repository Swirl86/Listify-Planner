package com.swirl.listifyplanner.presentation.calendar_screen

import TitleTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.calendar_screen.components.MyDatePicker
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.CalendarList
import com.swirl.listifyplanner.presentation.common.TextIconButton
import com.swirl.listifyplanner.presentation.common.colorpicker.ColorPickedDisplay
import com.swirl.listifyplanner.presentation.common.colorpicker.ColorPickerDialog
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.utils.UiText

@Composable
fun CalendarScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val calendarNotes by mainViewModel.getAllCalendarNotes.collectAsStateWithLifecycle(initialValue = emptyList())
    var chosenDate by remember { mutableStateOf("") }
    var chosenColor by remember { mutableStateOf(Color.LightGray) }
    var showColorPickerDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TitleTopAppBar(title = "Calendar") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                 horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyDatePicker(onButtonClick = { dateString ->
                chosenDate = dateString
            })
            TextIconButton(
                text =  UiText.StringResource(R.string.calendar_choose_color).asString(),
                icon = Icons.Default.ColorLens,
                contentDescription = UiText.StringResource(R.string.icon_color_lens).asString(),
                tint = TaskLightGreenBg,
                onClick = { showColorPickerDialog = true }
            )
            if (showColorPickerDialog) {
                ColorPickerDialog(
                    onDismiss = { showColorPickerDialog = !showColorPickerDialog },
                    onNegativeClick = { showColorPickerDialog = !showColorPickerDialog },
                    onPositiveClick = { color ->
                        showColorPickerDialog = !showColorPickerDialog
                        chosenColor = color
                    }
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            ColorPickedDisplay(color = chosenColor, height = 20)
            Spacer(modifier = Modifier.padding(6.dp))
            Box(
                modifier = Modifier.weight(1f).padding(vertical = 4.dp)
            ) {
                CalendarList()
            }
        }
    }
}