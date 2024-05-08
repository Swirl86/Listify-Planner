package com.swirl.listifyplanner.presentation.calendar_screen.components.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.utils.UiText

@Composable
fun EmptyStickyNote() {
    val titleFont = FontFamily(Font(R.font.permanent_marker))

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
                .background(Color.Cyan, RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray)
                .padding(16.dp)
        ) {
            Text(
                text = UiText.StringResource(R.string.calendar_note_empty_list).asString(),
                style = MaterialTheme.typography.h5.copy(fontFamily = titleFont),
                color = Color.DarkGray
            )
        }
    }
}
