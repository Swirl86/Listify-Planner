package com.swirl.listifyplanner.presentation.home_screen.components


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.swirl.listifyplanner.presentation.common.topAppBarTextStyle
import com.swirl.listifyplanner.presentation.home_screen.components.alert_dialogs.AlertDialog_General

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    size: Int,
    onDeleteAll: () -> Unit
) {

    var openDeleteAllDialog by rememberSaveable { mutableStateOf(false) }

    if (openDeleteAllDialog)
        AlertDialog_General(
            onDismissRequest = { openDeleteAllDialog = false },
            onConfirmation = {
                openDeleteAllDialog = false
                onDeleteAll()
            },
            dialogTitle = "Delete all todos",
            dialogText = "Are you sure you want to delete all your todos? They can not be restored once deleted!",
            icon = Icons.Rounded.Warning,
            modifier = Modifier.size(62.dp),
            tintColor = Color.Red
        )

    TopAppBar(
        title = { Text(text = "Todos", style = topAppBarTextStyle) },
        actions = {
            if (size > 0) {
                IconButton(
                    onClick = { openDeleteAllDialog = true }
                ) {
                    Icon(
                        Icons.Rounded.DeleteForever,
                        tint = Color.Red,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    )
}