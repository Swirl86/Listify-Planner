package com.swirl.listifyplanner.presentation.todo_screen.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.alert_dialogs.AlertDialogGeneral
import com.swirl.listifyplanner.ui.constants.DefaultIconSize
import com.swirl.listifyplanner.ui.constants.topAppBarTextStyle
import com.swirl.listifyplanner.utils.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    size: Int,
    onDeleteAll: () -> Unit
) {

    var openDeleteAllDialog by rememberSaveable { mutableStateOf(false) }

    if (openDeleteAllDialog)
        AlertDialogGeneral(
            onDismissRequest = { openDeleteAllDialog = false },
            onConfirmation = {
                openDeleteAllDialog = false
                onDeleteAll()
            },
            dialogTitle = UiText.StringResource(R.string.todo_delete_all_title).asString(),
            dialogText = UiText.StringResource(R.string.todo_delete_all_subtitle).asString(),
            icon = Icons.Rounded.Warning,
            modifier = Modifier.size(62.dp),
            tintColor = Color.Red
        )

    TopAppBar(
        title = { Text(text = "Todos", style = topAppBarTextStyle) },
        actions = {
            if (size > 0) {
                Icon(
                    modifier = Modifier
                        .size(DefaultIconSize)
                        .clickable { openDeleteAllDialog = true },
                    imageVector = Icons.Rounded.DeleteForever,
                    tint = Color.Red,
                    contentDescription = UiText.StringResource(R.string.icon_delete).asString()
                )
            }
        }
    )
}