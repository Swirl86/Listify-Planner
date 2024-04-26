package com.swirl.listifyplanner.presentation.calendar_screen

import TitleTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.swirl.listifyplanner.presentation.calendar_screen.components.MyDatePicker

@Composable
fun CalendarScreen() {
    val scrollState = rememberScrollState()

    /* TODO
    * - Create empty list view like no todos view
    * - Create db for calender objects
    * - Show list of dates and how many notes related, maybe dropdown to show each note
    * - Top part of view add new note related to picked date
    * - Functionality add, edit and delete
    */
    Scaffold(
        topBar = { TitleTopAppBar(title = "Calendar") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyDatePicker()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}