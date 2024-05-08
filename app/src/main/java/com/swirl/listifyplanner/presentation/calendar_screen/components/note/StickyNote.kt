package com.swirl.listifyplanner.presentation.calendar_screen.components.note

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.data.model.Note
import com.swirl.listifyplanner.utils.extenstions.dateTimeToString

@Composable
fun StickyNote(
    note: Note,
    onNoteClicked: () -> Unit,
    onNoteChanged: (Note) -> Unit
) {
    var editedNote by remember { mutableStateOf(note) }

    Surface(
        modifier = Modifier
            .padding(8.dp)
            .width(200.dp)
            .clickable(onClick = onNoteClicked)
            .background(note.color)
            .border(1.dp, Color.Black)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            BasicTextField(
                value = editedNote.title,
                onValueChange = { editedNote = editedNote.copy(title = it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = editedNote.description,
                onValueChange = { editedNote = editedNote.copy(description = it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = editedNote.timeStamp.dateTimeToString()
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (editedNote != note) {
                onNoteChanged(editedNote)
            }
        }
    }
}
