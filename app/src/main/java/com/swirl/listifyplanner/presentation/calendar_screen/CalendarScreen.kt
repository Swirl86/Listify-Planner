package com.swirl.listifyplanner.presentation.calendar_screen

import TitleTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.calendar_screen.components.CalendarList
import com.swirl.listifyplanner.presentation.calendar_screen.components.CalendarScreenHeader
import com.swirl.listifyplanner.presentation.calendar_screen.components.ChosenDate
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.AddNoteToDateScreen
import com.swirl.listifyplanner.presentation.common.DraggableComponent
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.convertLocalDateToMillis
import java.time.LocalDate

@Composable
fun CalendarScreen(
    mainViewModel: MainViewModel,
    onAddNote: (date: LocalDate) -> Unit
) {
    val calenderNotes by mainViewModel.getAllCalendarNotes.collectAsStateWithLifecycle(initialValue = emptyList())
    val chosenDate = remember { mutableStateOf<LocalDate?>(null) }

    // TODO make every datecard clickable to see notes for date or add new to selected date
    // ExtendedFloatingActionButton
    val listState = rememberLazyListState()
    val isExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    Scaffold(
        topBar = { TitleTopAppBar(title = "Calendar") },
        floatingActionButton = {
            chosenDate.value?.let {
                DraggableComponent {
                    ExtendedFloatingActionButton(
                        text = { Text(text = UiText.StringResource(R.string.calendar_note).asString()) },
                        icon = { Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = UiText.StringResource(R.string.icon_add).asString()
                        ) },
                        expanded = isExpanded,
                        onClick = {
                            onAddNote((chosenDate.value ?: LocalDate.now()))
                        }
                    )
                }
            }
        }
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
            Spacer(modifier = Modifier.padding(6.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            ) {
                chosenDate.value?.let { date ->
                    val notes = calenderNotes.find { it.date == date }?.notes ?: emptyList()
                    ChosenDate(date, notes)
                } ?: CalendarList(calenderNotes)
            }
        }
    }
}