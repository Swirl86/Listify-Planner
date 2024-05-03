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
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DayCard(date: LocalDate) {
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
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style =  MaterialTheme.typography.h1,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                color = Color.Red
            )
            Text(
                text = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()).capitalizeWord(),
                style =  MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(6.dp, end = 12.dp)
                    .align(Alignment.CenterVertically)
            )

            Box(
                modifier = Modifier
                    .background(Color.LightGray, RoundedCornerShape(12.dp))
                    .fillMaxHeight()
                    .width(4.dp)
                    .padding(8.dp)
            )

            // TODO add date notes
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(text = "Lorem Ipsum")
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = "Lorem Ipsum")
                Spacer(modifier = Modifier.padding(6.dp))
                Text(text = "Lorem Ipsum")
            }
        }
    }
}