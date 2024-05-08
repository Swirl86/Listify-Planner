package com.swirl.listifyplanner.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.utils.UiText

@Composable
fun CloseX(
    iconSize: Int = 32,
    onCloseClick: () -> Unit
) {
    val iconModifier = Modifier.size(iconSize.dp).padding(4.dp)
    val boxSize = iconSize + 32
    Box(
        modifier = Modifier
            .size(boxSize.dp)
            .wrapContentSize(Alignment.TopEnd)
            .padding(end = 8.dp, top = 8.dp)
            .clickable { onCloseClick() }
            .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = UiText.StringResource(R.string.button_close).asString(),
            tint = Color.Black,
            modifier = iconModifier
        )
    }
}

@Preview
@Composable
fun CloseXPreview() {
    CloseX(onCloseClick = { /* Handle close action */ })
}