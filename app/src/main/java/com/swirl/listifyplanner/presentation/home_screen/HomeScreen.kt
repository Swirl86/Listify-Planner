package com.swirl.listifyplanner.presentation.home_screen

import android.graphics.Paint
import android.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.ui.theme.Purple80
import com.swirl.listifyplanner.utils.UiText

@Composable
fun HomeScreen() {
    val title = UiText.StringResource(R.string.home_welcome_text).asString()
    Column(
        Modifier.fillMaxSize().background(color = colorResource(R.color.splash_green)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.width(350.dp).height(100.dp)) {
            drawIntoCanvas {
                val textPadding = 48.dp.toPx()
                val arcHeight = 400.dp.toPx()
                val arcWidth = 350.dp.toPx()

                val path = Path().apply {
                    addArc(0f, textPadding, arcWidth, arcHeight, 180f, 180f)
                }

                it.nativeCanvas.drawTextOnPath(
                    title,
                    path,
                    0f,
                    0f,
                    Paint().apply {
                        textSize = 28.sp.toPx()
                        textAlign = Paint.Align.CENTER
                        color = Purple80.toArgb()
                    }
                )
            }
        }
        Image(
            painter = painterResource(R.drawable.ic_splash_icon),
            contentDescription = UiText.StringResource(R.string.img_app_icon).asString(),
            modifier = Modifier.size(150.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}