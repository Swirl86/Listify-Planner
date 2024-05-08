package com.swirl.listifyplanner.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.utils.UiText


@Composable
fun RequiredField() {
    Row(
        modifier = Modifier.padding(top = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = UiText.StringResource(R.string.required).asString(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            textAlign = TextAlign.Start,
            color = Color.Red,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}