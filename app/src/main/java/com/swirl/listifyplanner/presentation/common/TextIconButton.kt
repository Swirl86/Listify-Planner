package com.swirl.listifyplanner.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.ui.theme.DeepPurple700
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.getButtonElevation

@Composable
fun TextIconButton(
    text: String,
    icon: ImageVector? = null,
    contentDescription: String,
    tint: Color = Color.White,
    iconSize: Int = 24,
    padding: Int = 6,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp),
        elevation = getButtonElevation(),
        border = BorderStroke(2.dp, DeepPurple700),
    ) {
        Text(
            modifier = Modifier
                .padding(padding.dp),
            text = text,
            style = style
        )
        icon?.let {
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                modifier = Modifier.size(iconSize.dp),
                imageVector = icon,
                tint = tint,
                contentDescription = contentDescription
            )
        }
    }
}

@Preview
@Composable
fun TextWithIconButtonPreview() {
    TextIconButton(
        text =  UiText.StringResource(R.string.calendar_top_title).asString(),
        icon = Icons.Default.CalendarMonth,
        contentDescription = UiText.StringResource(R.string.icon_calendar).asString(),
        tint = TaskLightGreenBg,
        onClick = {}
    )
}