package com.swirl.listifyplanner.presentation.common.colorpicker

import androidx.compose.runtime.MutableFloatState
import kotlin.random.Random

fun randomColor(
    red: MutableFloatState,
    green: MutableFloatState,
    blue: MutableFloatState,
    alpha: MutableFloatState
) {
    red.floatValue = Random.nextFloat().coerceIn(0f, 1f)
    green.floatValue = Random.nextFloat().coerceIn(0f, 1f)
    blue.floatValue = Random.nextFloat().coerceIn(0f, 1f)
    alpha.floatValue = Random.nextFloat().coerceIn(0f, 1f)
}