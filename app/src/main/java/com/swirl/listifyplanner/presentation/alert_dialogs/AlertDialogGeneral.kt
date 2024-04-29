package com.swirl.listifyplanner.presentation.alert_dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.utils.UiText


@Composable
fun AlertDialogGeneral(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    modifier: Modifier,
    tintColor: Color
) {
    AlertDialog(
        icon = {
            Icon(icon, modifier = modifier, tint = tintColor, contentDescription = UiText.StringResource(R.string.icon_general_alert_dialog).asString())
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(UiText.StringResource(R.string.button_confirm).asString())
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(UiText.StringResource(R.string.button_dismiss).asString())
            }
        }
    )
}