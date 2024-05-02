package com.swirl.listifyplanner.presentation.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.alert_dialogs.AlertDialogAddScreen
import com.swirl.listifyplanner.presentation.common.DraggableComponent
import com.swirl.listifyplanner.presentation.common.EmptyScreen
import com.swirl.listifyplanner.presentation.common.SwipeToDeleteContainer
import com.swirl.listifyplanner.presentation.common.snackbar
import com.swirl.listifyplanner.presentation.home_screen.components.MyTopAppBar
import com.swirl.listifyplanner.presentation.home_screen.components.TodoCard
import com.swirl.listifyplanner.utils.UiText

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onUpdate: (id: Int) -> Unit
) {
    val context = LocalContext.current

    val todos by mainViewModel.getAllTodos.collectAsStateWithLifecycle(initialValue = emptyList())
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // ExtendedFloatingActionButton
    val listState = rememberLazyListState()
    val isExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MyTopAppBar(size = todos.size, onDeleteAll = { mainViewModel.deleteAllTodos() })
        },
        floatingActionButton = {
            DraggableComponent {
                ExtendedFloatingActionButton(
                    text = { Text(text = UiText.StringResource(R.string.add_todo).asString()) },
                    icon = { Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = UiText.StringResource(R.string.icon_add).asString()
                    ) },
                    expanded = isExpanded,
                    onClick = { openDialog = true }
                )
            }
        }
    ) { paddingValues ->
        AlertDialogAddScreen(
            openDialog = openDialog,
            onClose = { openDialog = false },
            mainViewModel = mainViewModel
        )
        if (todos.isEmpty()) {
            EmptyScreen(
                text = UiText.StringResource(R.string.empty_home_screen_title).asString(),
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
                        items = todos.sortedBy { it.timeStamp },
                        key = {it.id}
                    ) { todo ->
                        SwipeToDeleteContainer(
                            item = todo,
                            onDelete = { mainViewModel.deleteTodo(todo) }
                        ) {
                            TodoCard(
                                todo = todo,
                                onDelete = {
                                    mainViewModel.deleteTodo(todo)
                                    snackbar(
                                        scope,
                                        snackbarHostState,
                                        message = UiText.StringResource(R.string.home_deleted_task, todo.task).asString(context),
                                        actionLabel = UiText.StringResource(R.string.home_undo_delete).asString(context),
                                        onAction = {mainViewModel.undoDeletedTodo()}
                                    )
                                },
                                onUpdate = onUpdate,
                                onClick = {
                                    mainViewModel.updateTodoIsDone(
                                        todo.copy(isDone = !todo.isDone)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}