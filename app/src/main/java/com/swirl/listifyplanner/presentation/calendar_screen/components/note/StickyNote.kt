package com.swirl.listifyplanner.presentation.calendar_screen.components.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.data.model.Note
import com.swirl.listifyplanner.presentation.common.AutoSizingText
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.dateTimeToString
import com.swirl.listifyplanner.utils.getTextColor

@Composable
fun StickyNote(
    note: Note,
    onNoteClicked: () -> Unit
) {
    val titleFont = FontFamily(Font(R.font.permanent_marker))
    val textColor = getTextColor(note.color)

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp)
                .zIndex(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pin),
                contentDescription = UiText.StringResource(R.string.img_pin).asString(),
                modifier = Modifier
                    .size(48.dp)
                    .rotate(20f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clickable(onClick = onNoteClicked)
                .background(note.color, RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray)
                .padding(16.dp)
        ) {
            AutoSizingText(
                modifier = Modifier.align(alignment = Alignment.Start).padding(end = 8.dp),
                text = note.title,
                style = MaterialTheme.typography.h5.copy(fontFamily = titleFont),
                color = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.description,
                style = MaterialTheme.typography.body1,
                fontFamily = FontFamily.SansSerif,
                color = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.timeStamp.dateTimeToString(),
                style = MaterialTheme.typography.body2,
                fontStyle = FontStyle.Italic,
                fontSize = 10.sp,
                color = textColor
            )
        }
    }
}
