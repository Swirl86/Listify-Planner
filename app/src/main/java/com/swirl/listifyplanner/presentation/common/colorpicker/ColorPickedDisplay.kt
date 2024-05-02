package com.swirl.listifyplanner.presentation.common.colorpicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ColorPickedDisplay(
    color: Color,
    paddingH: Int = 10,
    paddingV: Int = 0,
    height: Int = 80,
    borderStroke: Int = 2,
    borderColor: Color = Color.DarkGray
) {
    Row {
        Box(
            modifier = Modifier
                .padding(paddingH.dp, paddingV.dp)
                .fillMaxWidth()
                .height(height.dp)
                .background(color, shape = MaterialTheme.shapes.large)
                .border(
                    BorderStroke(borderStroke.dp, SolidColor(borderColor)),
                    shape = RoundedCornerShape(15.dp)
                )
        )
    }
}

@Preview
@Composable
fun ColorPickedDisplayPreview() {
    ColorPickedDisplay(color = Color.Green, height = 20)
}