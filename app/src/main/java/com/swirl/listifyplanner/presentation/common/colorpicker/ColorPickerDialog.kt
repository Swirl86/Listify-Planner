package com.swirl.listifyplanner.presentation.common.colorpicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.common.CloseX
import com.swirl.listifyplanner.presentation.common.TextIconButton
import com.swirl.listifyplanner.ui.constants.DefaultIconSize
import com.swirl.listifyplanner.utils.UiText

@Composable
fun ColorPickerDialog(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Color) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val alpha = rememberSaveable { mutableFloatStateOf(1f) }
    val red = rememberSaveable { mutableFloatStateOf(0f) }
    val green = rememberSaveable { mutableFloatStateOf(0f) }
    val blue = rememberSaveable { mutableFloatStateOf(0f) }

    // Generate a random starting color
    randomColor(red, green, blue, alpha)

    val color by remember {
        derivedStateOf {
            Color(red.floatValue, green.floatValue, blue.floatValue, alpha.floatValue)
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row {
                    Text(
                        text = UiText.StringResource(R.string.calendar_choose_color).asString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                    CloseX(onCloseClick = onNegativeClick)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(6.dp)) {
                    
                    Box {
                        ColorPickedDisplay(color)
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 12.dp, end = 22.dp)
                                .clickable(
                                    onClick = { randomColor(red, green, blue, alpha) },
                                    indication = null,
                                    interactionSource = interactionSource
                                )
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_dice),
                                contentDescription = UiText.StringResource(R.string.img_dice)
                                    .asString(),
                                modifier = Modifier
                                    .size(DefaultIconSize)
                                    .background(Color.LightGray, shape = CircleShape)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        ColorSlider("A", alpha, color.copy(1f))
                        ColorSlider("R", red, Color.Red)
                        ColorSlider("G", green, Color.Green)
                        ColorSlider("B", blue, Color.Blue)
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextIconButton(
                        text =  UiText.StringResource(R.string.button_ok).asString(),
                        icon = null,
                        contentDescription = "",
                        onClick = {  onPositiveClick(color) }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun ColorPickerPreview() {
    ColorPickerDialog(
        onDismiss = {},
        onNegativeClick = {},
        onPositiveClick = { /* Handle color selection */ }
    )
}