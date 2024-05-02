package com.swirl.listifyplanner.presentation.calendar_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.data.model.CalendarNote
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.common.taskTextStyle
import com.swirl.listifyplanner.presentation.common.toastMsg
import com.swirl.listifyplanner.utils.UiText
import kotlinx.coroutines.job
import java.time.LocalDate

@Composable
fun AddNoteToDateScreen(
    openDialog: Boolean,
    onClose: () -> Unit,
    mainViewModel: MainViewModel,
    chosenDate: LocalDate,
    chosenColor: Color = Color.LightGray
) {
    var text by remember {  mutableStateOf("") }

    val note = CalendarNote(chosenDate, chosenColor)

    val focusRequest = FocusRequester()
    val context = LocalContext.current

    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = UiText.StringResource(R.string.calendar_add).asString(),
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
                            text = UiText.StringResource(R.string.calendar_add_note_title).asString()
                        ) },
                        onValueChange = { text = it },
                        placeholder = {
                            Text(
                                text = UiText.StringResource(R.string.calendar_add_note_sub_title).asString(),
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
                                    mainViewModel.insertCalendarNote(note)
                                    text = ""
                                    onClose()
                                } else {
                                    toastMsg(
                                        context,
                                        UiText.StringResource(R.string.calendar_add_not_empty).asString(context)
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
                        textStyle = taskTextStyle()  // TODO update here
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            },
            onDismissRequest = {
                onClose()
                text = ""
            },
            confirmButton = {
                Button(
                    enabled = text.isNotEmpty(),
                    onClick = {
                        if (text.isNotBlank()) {
                            mainViewModel.insertCalendarNote(note)
                            text = ""
                            onClose()
                        } else {
                            toastMsg(
                                context,
                                UiText.StringResource(R.string.calendar_add_not_empty).asString(context)
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
                }) {
                    Text(text = UiText.StringResource(R.string.button_close).asString())
                }
            }
        )
    }

}