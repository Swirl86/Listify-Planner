package com.swirl.listifyplanner.presentation.calendar_screen.components.date

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.data.model.Note
import com.swirl.listifyplanner.presentation.common.AutoSizingText
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayCard(date: LocalDate, notes: List<Note>) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
            Box(
                modifier = Modifier
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
                    .fillMaxHeight()
                    .width(4.dp)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (notes.isNotEmpty()) {
                    val limitedList = notes.take(3)
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        for (item in limitedList) {
                            Text(
                                text = item.title,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = MaterialTheme.typography.body2
                            )
                            Spacer(modifier = Modifier.padding(6.dp))
                        }
                    }
                }
            }
        }
    }
}