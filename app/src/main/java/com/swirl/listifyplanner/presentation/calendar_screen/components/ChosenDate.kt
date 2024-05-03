package com.swirl.listifyplanner.presentation.calendar_screen.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.DayCard
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.MonthHeader
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.WeekNumberHeader
import com.swirl.listifyplanner.utils.extenstions.isoWeekNumber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ChosenDate(chosenDate: LocalDate) {
    val lazyListState = rememberLazyListState()
    val monthTitle = "${
        chosenDate.month.getDisplayName(
            TextStyle.FULL_STANDALONE,
            Locale.getDefault()
        )
    } ${chosenDate.year} "

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        item {
            MonthHeader(monthTitle)
            WeekNumberHeader(chosenDate.isoWeekNumber)
            DayCard(chosenDate)
        }
    }
}