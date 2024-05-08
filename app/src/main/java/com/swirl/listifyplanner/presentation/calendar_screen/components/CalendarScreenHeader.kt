package com.swirl.listifyplanner.presentation.calendar_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.picker.MyDatePicker
import com.swirl.listifyplanner.presentation.common.TextIconButton
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.utils.UiText
import java.time.LocalDate

@Composable
fun CalendarScreenHeader(isEnabled: Boolean, onButtonClick: (LocalDate?) -> Unit) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextIconButton(
            text = UiText.StringResource(R.string.calendar_top_title).asString(),
            icon = Icons.Default.CalendarMonth,
            contentDescription = UiText.StringResource(R.string.icon_calendar).asString(),
            tint = TaskLightGreenBg,
            onClick = { showDialog.value = true }
        )
        Spacer(modifier = Modifier.width(6.dp))
        TextIconButton(
            text = UiText.StringResource(R.string.button_clear).asString(),
            icon = Icons.Default.Close,
            contentDescription = UiText.StringResource(R.string.button_clear).asString(),
            tint = Color.Red,
            onClick = { onButtonClick(null) },
            isEnabled = isEnabled
        )
        if (showDialog.value) {
            MyDatePicker(showDialog, onPositiveClick = { date ->
                onButtonClick(date)
            })
        }
    }
}