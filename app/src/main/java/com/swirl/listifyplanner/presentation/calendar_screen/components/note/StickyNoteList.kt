package com.swirl.listifyplanner.presentation.calendar_screen.components.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.data.model.Note

@Composable
fun StickyNoteList(
    notes: List<Note>,
    onNoteClicked: (Note) -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        notes.forEach { note ->
            StickyNote(
                note = note,
                onNoteClicked = { onNoteClicked(note) },
                onNoteChanged = {}
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}