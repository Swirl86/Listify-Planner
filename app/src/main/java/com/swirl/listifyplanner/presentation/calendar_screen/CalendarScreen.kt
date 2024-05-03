package com.swirl.listifyplanner.presentation.calendar_screen

import TitleTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.calendar_screen.components.CalendarList
import com.swirl.listifyplanner.presentation.calendar_screen.components.CalendarScreenHeader
import com.swirl.listifyplanner.presentation.calendar_screen.components.ChosenDate
import java.time.LocalDate

@Composable
fun CalendarScreen(mainViewModel: MainViewModel) {
    val chosenDate = remember { mutableStateOf<LocalDate?>(null) }

    Scaffold(
        topBar = { TitleTopAppBar(title = "Calendar") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
                 horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarScreenHeader((chosenDate.value != null)) { date ->
                chosenDate.value = date
            }

            // TODO move color picker
            /*

            var chosenColor by remember { mutableStateOf(Color.LightGray) }
            var showColorPickerDialog by remember { mutableStateOf(false) }

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
            ColorPickedDisplay(color = chosenColor, height = 20)*/
            Spacer(modifier = Modifier.padding(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            ) {
                chosenDate.value?.let {
                    ChosenDate(it)
                } ?: CalendarList()
            }
        }
    }
}