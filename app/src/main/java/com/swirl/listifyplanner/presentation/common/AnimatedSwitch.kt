package com.swirl.listifyplanner.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.utils.getSwitchDefaultColors

@Composable
fun AnimatedSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    thumbIcon: ImageVector,
    thumbSize: Int = 24,
    animationDuration: Int = 1000
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = Modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = animationDuration,
                easing = FastOutSlowInEasing
            )
        ),
        thumbContent = {
            Icon(
                imageVector = thumbIcon,
                contentDescription = null,
                modifier = Modifier.size(thumbSize.dp)
            )
        },
        colors = getSwitchDefaultColors()
    )
}
