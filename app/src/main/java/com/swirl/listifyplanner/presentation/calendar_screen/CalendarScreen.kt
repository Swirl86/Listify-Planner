package com.swirl.listifyplanner.presentation.calendar_screen

import TitleTopAppBar
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.calendar_screen.components.MyDatePicker
import com.swirl.listifyplanner.presentation.common.EmptyScreen
import com.swirl.listifyplanner.presentation.common.SwipeToDeleteContainer
import com.swirl.listifyplanner.utils.UiText

@Composable
fun CalendarScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val calendarNotes by mainViewModel.getAllCalendarNotes.collectAsStateWithLifecycle(initialValue = emptyList())
    val listState = rememberLazyListState()
    var chosenDate by remember { mutableStateOf("") }

    /* TODO
    *
    * - Show list of dates and how many notes related, maybe dropdown to show each note
    * - Top part of view add new note related to picked date
    * - Functionality add, edit and delete
    */
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
            Row(
                modifier = Modifier
                .fillMaxSize()
            ) {
                MyDatePicker(onButtonClick = { dateString ->
                    // TODO open create note for date
                    chosenDate = dateString
                })
            }
            if (chosenDate.isNotEmpty()) Text(text = chosenDate) // TODO remove
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                if (calendarNotes.isEmpty()) {
                    EmptyScreen(
                        text = UiText.StringResource(R.string.empty_calendar_title).asString(context),
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