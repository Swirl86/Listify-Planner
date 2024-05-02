package com.swirl.listifyplanner.presentation.calendar_screen

import TitleTopAppBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.calendar_screen.components.MyDatePicker
import com.swirl.listifyplanner.presentation.common.EmptyScreen
import com.swirl.listifyplanner.presentation.common.SwipeToDeleteContainer
import com.swirl.listifyplanner.presentation.common.TextIconButton
import com.swirl.listifyplanner.presentation.common.colorpicker.ColorPickedDisplay
import com.swirl.listifyplanner.presentation.common.colorpicker.ColorPickerDialog
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.utils.UiText

@Composable
fun CalendarScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val calendarNotes by mainViewModel.getAllCalendarNotes.collectAsStateWithLifecycle(initialValue = emptyList())
    val listState = rememberLazyListState()
    var chosenDate by remember { mutableStateOf("") }
    var chosenColor by remember { mutableStateOf(Color.LightGray) }
    var showColorPickerDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TitleTopAppBar(title = "Calendar") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(state = scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MyDatePicker(onButtonClick = { dateString ->
                    chosenDate = dateString
                })
                TextIconButton(
                    text =  UiText.StringResource(R.string.calendar_choose_color).asString(),
                    icon = Icons.Default.ColorLens,
                    contentDescription = UiText.StringResource(R.string.icon_color_lens).asString(),
                    tint = TaskLightGreenBg,
                    onClick = { showColorPickerDialog = true }
                )
                if (showColorPickerDialog) {
                    ColorPickerDialog(
                        onDismiss = { showColorPickerDialog = !showColorPickerDialog },
                        onNegativeClick = { showColorPickerDialog = !showColorPickerDialog },
                        onPositiveClick = { color ->
                            showColorPickerDialog = !showColorPickerDialog
                            chosenColor = color
                        }
                    )
                }
            }
            ColorPickedDisplay(color = chosenColor, height = 20)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                if (calendarNotes.isEmpty()) {
                    EmptyScreen(
                        text = UiText.StringResource(R.string.empty_calendar_title).asString(),
                        paddingValues = paddingValues
                    )
                } else {
                    AnimatedVisibility(
                        visible = true,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = listState
                        ) {
                            items(
                                items = calendarNotes.sortedBy { it.date },
                                key = { it.date }
                            ) { calendarNote ->
                                SwipeToDeleteContainer(
                                    item = calendarNote,
                                    onDelete = { mainViewModel.deleteCalendarNote(calendarNote) }
                                ) {
                                    // TODO update design for CalendarNote
                                    /*TodoCard(
                                        todo = todo,
                                        onDelete = {
                                            mainViewModel.deleteTodo(todo)
                                            snackbar(
                                                scope,
                                                snackbarHostState,
                                                message = UiText.StringResource(
                                                    R.string.home_deleted_task,
                                                    todo.task
                                                ).asString(context),
                                                actionLabel = UiText.StringResource(R.string.home_undo_delete)
                                                    .asString(context),
                                                onAction = { mainViewModel.undoDeletedTodo() }
                                            )
                                        },
                                        onUpdate = onUpdate,
                                        onClick = {
                                            mainViewModel.updateTodoIsDone(
                                                todo.copy(isDone = !todo.isDone)
                                            )
                                        }
                                    )*/
                                    Text(text = "HERE")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}