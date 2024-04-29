package com.swirl.listifyplanner.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.ui.theme.DeepPurple700
import com.swirl.listifyplanner.utils.getButtonElevation

@Composable
fun TextWithIconButton(
    text: String,
    icon: ImageVector,
    contentDescription: String,
    tint: Color = Color.White,
    iconSize: Int = 32,
    padding: Int = 8,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
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
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Icon(
                modifier = Modifier.size(iconSize.dp),
                imageVector = icon,
                tint = tint,
                contentDescription = contentDescription
         )

    }
    /*Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(end = padding.dp),
            style = style
        )
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.size(iconSize.dp),
                imageVector = icon,
                tint = tint,
                contentDescription = contentDescription
            )
        }
    }*/
}
