package com.swirl.listifyplanner.presentation.todo_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.alert_dialogs.AlertDialogGeneral
import com.swirl.listifyplanner.presentation.common.AnimatedSwitch
import com.swirl.listifyplanner.presentation.common.drawAnimationBorder
import com.swirl.listifyplanner.ui.constants.DefaultDp
import com.swirl.listifyplanner.ui.constants.DefaultIconSize
import com.swirl.listifyplanner.ui.constants.MediumDp
import com.swirl.listifyplanner.ui.constants.topAppBarTextStyle
import com.swirl.listifyplanner.ui.preview.ProvideMainViewModelForPreview
import com.swirl.listifyplanner.utils.UiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTodoScreen(
    id: Int,
    mainViewModel: MainViewModel,
    onBack: () -> Unit
) {
    val task = mainViewModel.todo.task
    val isImportant = mainViewModel.todo.isImportant
    var openDeleteDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(
        key1 = true,
        block = {
            mainViewModel.getTodoById(id)
        })

    if (openDeleteDialog)
        AlertDialogGeneral(
            onDismissRequest = { openDeleteDialog = false },
            onConfirmation = {
                openDeleteDialog = false
                mainViewModel.deleteTodo(mainViewModel.todo)
                onBack()
            },
            dialogTitle = UiText.StringResource(R.string.todo_delete_title).asString(),
            dialogText = UiText.StringResource(R.string.todo_delete_subtitle).asString(),
            icon = Icons.Rounded.Warning,
            modifier = Modifier.size(62.dp),
            tintColor = Color.Red
        )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UiText.StringResource(R.string.update_todo).asString(),
                            style = topAppBarTextStyle
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier
                                .size(DefaultIconSize)
                                .clickable { openDeleteDialog = true },
                            imageVector = Icons.Rounded.DeleteForever,
                            tint = Color.Red,
                            contentDescription = UiText.StringResource(R.string.icon_delete).asString()
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = UiText.StringResource(R.string.icon_arrow_back).asString()
                        )
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(DefaultDp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(DefaultDp))
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = UiText.StringResource(R.string.icon_edit).asString(),
                modifier = Modifier
                    .size(120.dp)
                    .drawAnimationBorder(
                        strokeWidth = 2.dp,
                        durationMillis = 3500,
                        shape = RoundedCornerShape(100)
                    )
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = task,
                onValueChange = { newValue ->
                    mainViewModel.updateTask(newValue)
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Task description...") },
                shape = RoundedCornerShape(MediumDp)
            )
            Spacer(modifier = Modifier.height(DefaultDp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumDp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = UiText.StringResource(R.string.todo_important).asString(),
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.width(DefaultDp))
                AnimatedSwitch(
                    checked = isImportant,
                    onCheckedChange = { newValue ->
                        mainViewModel.updateIsImportant(newValue)
                    },
                    if (isImportant) Icons.Filled.Check else Icons.Filled.Close
                )
            }
            Spacer(modifier = Modifier.height(DefaultDp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        mainViewModel.updateTodo(mainViewModel.todo)
                        onBack()
                    },
                    modifier = Modifier
                        .size(width = 150.dp, height = 60.dp)
                ) {
                    Text(
                        text = UiText.StringResource(R.string.button_save).asString(),
                        fontSize = 18.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(DefaultDp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateTodoScreenPreview() {
    ProvideMainViewModelForPreview {
        val mainViewModel: MainViewModel = viewModel()
        UpdateTodoScreen(
            0,
            mainViewModel,
            onBack = {  }
        )
    }
}