package com.swirl.listifyplanner.presentation.todo_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.ui.constants.taskTextStyle
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

    LaunchedEffect(
        key1 = true,
        block = {
            mainViewModel.getTodoById(id)
    })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = UiText.StringResource(R.string.update_todo).asString(),
                        style = topAppBarTextStyle
                    )
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
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = UiText.StringResource(R.string.icon_edit).asString(),
                modifier = Modifier
                    .size(100.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            TextField(
                value = task,
                onValueChange = { newValue ->
                    mainViewModel.updateTask(newValue)
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = UiText.StringResource(R.string.update_todo_task).asString(),
                        fontFamily = FontFamily.Monospace
                    )
                },
                shape = RectangleShape,
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.Sentences
                ),
                textStyle = taskTextStyle()
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = UiText.StringResource(R.string.todo_important).asString(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(8.dp))
                Checkbox(checked = isImportant, onCheckedChange = { newValue ->
                    mainViewModel.updateIsImportant(newValue)
                })
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(onClick = {
                mainViewModel.updateTodo(mainViewModel.todo)
                onBack()
            }) {
                Text(
                    text = UiText.StringResource(R.string.update_todo_save).asString(),
                    fontSize = 16.sp
                )
            }
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
            onBack = { /*TODO*/ }
        )
    }
}