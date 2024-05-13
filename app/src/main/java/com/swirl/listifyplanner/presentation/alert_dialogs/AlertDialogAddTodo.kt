package com.swirl.listifyplanner.presentation.alert_dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.data.model.Todo
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.common.toastMsg
import com.swirl.listifyplanner.ui.constants.taskTextStyle
import com.swirl.listifyplanner.utils.UiText
import kotlinx.coroutines.job
import java.time.LocalDateTime

/**
 * Add a new todo_object from home_screen or speech_to_text screen
 */
@Composable
fun AlertDialogAddScreen(
    openDialog: Boolean,
    onClose: () -> Unit,
    mainViewModel: MainViewModel,
    todoText: String? = null /* Value from speech_to_text screen */
) {
    var text by remember {  mutableStateOf("") }
    var isImportant by remember { mutableStateOf(false) }
    todoText?.let { text = it }

    val todo = Todo(0, text, LocalDateTime.now(), isImportant)

    val focusRequest = FocusRequester()
    val context = LocalContext.current

    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = UiText.StringResource(R.string.add_todo).asString(),
                    fontFamily = FontFamily.Serif
                )
            },
            text = {
                LaunchedEffect(key1 = true) {
                    coroutineContext.job.invokeOnCompletion {
                        focusRequest.requestFocus()
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = text,
                        label = { Text(
                            text = UiText.StringResource(R.string.add_todo_title).asString()
                        ) },
                        onValueChange = { text = it },
                        placeholder = {
                            Text(
                                text = UiText.StringResource(R.string.add_todo_sub_title).asString(),
                                fontFamily = FontFamily.Monospace
                            )
                        },
                        shape = RectangleShape,
                        modifier = Modifier.focusRequester(focusRequest),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (text.isNotBlank()) {
                                    mainViewModel.insertTodo(todo)
                                    text = ""
                                    isImportant = false
                                    onClose()
                                } else {
                                    toastMsg(
                                        context,
                                        UiText.StringResource(R.string.add_todo_empty).asString(context)
                                    )
                                }
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = { text = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = null
                                )
                            }
                        },
                        textStyle = taskTextStyle()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = UiText.StringResource(R.string.todo_important).asString(),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Checkbox(
                            checked = isImportant,
                            onCheckedChange = { isImportant = it }
                        )
                    }
                }
            },
            onDismissRequest = {
                onClose()
                text = ""
                isImportant = false
            },
            confirmButton = {
                Button(
                    enabled = text.isNotEmpty(),
                    onClick = {
                        if (text.isNotBlank()) {
                            mainViewModel.insertTodo(todo)
                            text = ""
                            isImportant = false
                            onClose()
                        } else {
                            toastMsg(
                                context,
                                UiText.StringResource(R.string.add_todo_empty).asString(context)
                            )
                        }
                    }
                ) {
                    Text(text = UiText.StringResource(R.string.button_save).asString())
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    onClose()
                    text = ""
                    isImportant = false
                }) {
                    Text(text = UiText.StringResource(R.string.button_close).asString())
                }
            }
        )
    }
}