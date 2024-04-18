package com.swirl.listifyplanner.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.common.snackbar
import com.swirl.listifyplanner.presentation.home_screen.components.alert_dialogs.AlertDialog_AddScreen
import com.swirl.listifyplanner.presentation.home_screen.components.EmptyToDoScreen
import com.swirl.listifyplanner.presentation.home_screen.components.MyTopAppBar
import com.swirl.listifyplanner.presentation.home_screen.components.TodoCard
import com.swirl.listifyplanner.presentation.home_screen.components.alert_dialogs.AlertDialog_General

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onUpdate: (id: Int) -> Unit
) {
    val todos by mainViewModel.getAllTodos.collectAsStateWithLifecycle(initialValue = emptyList())
    var openDialog by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MyTopAppBar(size = todos.size, onDeleteAll = { mainViewModel.deleteAllTodos() })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { openDialog = true }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        AlertDialog_AddScreen(
            openDialog = openDialog,
            onClose = { openDialog = false },
            mainViewModel = mainViewModel
        )

        if (todos.isEmpty()) {
            EmptyToDoScreen(paddingValues = paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = todos,
                    key = {it.id}
                ) { todo ->
                    TodoCard(
                        todo = todo,
                        onDelete = {
                            mainViewModel.deleteTodo(todo)
                            snackbar(
                                scope,
                                snackbarHostState,
                                message = "Deleted task: \"${todo.task}\"",
                                actionLabel = "UNDO DELETE",
                                onAction = {mainViewModel.undoDeletedTodo()}
                            )
                        },
                        onUpdate = onUpdate
                    )
                }
            }
        }
    }
}