package com.swirl.listifyplanner.presentation.calendar_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.data.model.Note
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.MonthHeader
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.WeekNumberHeader
import com.swirl.listifyplanner.presentation.calendar_screen.components.note.StickyNoteList
import com.swirl.listifyplanner.presentation.common.AutoSizingText
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord
import com.swirl.listifyplanner.utils.extenstions.isoWeekNumber
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun ChosenDate(date: LocalDate, notes: List<Note>) {
    val lazyListState = rememberLazyListState()

    val monthTitle = "${
        date.month.getDisplayName(
            TextStyle.FULL_STANDALONE,
            Locale.getDefault()
        )
    } ${date.year} "

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        state = lazyListState
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    MonthHeader(monthTitle)
                    WeekNumberHeader(date.isoWeekNumber)
                }

                Spacer(modifier = Modifier.padding(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        maxLines = 1,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    AutoSizingText(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()).capitalizeWord(),
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))
            StickyNoteList(notes = notes) {
                // TODO impl edit view like updateTodo
            }
        }
    }
}