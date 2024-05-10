package com.swirl.listifyplanner.presentation.calendar_screen.components.date

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.data.model.Note
import com.swirl.listifyplanner.data.repository.CalendarNoteRepository
import com.swirl.listifyplanner.data.repository.TodoRepository
import com.swirl.listifyplanner.db.dao.CalendarNoteDao
import com.swirl.listifyplanner.db.dao.TodoDao
import com.swirl.listifyplanner.di.DatabaseModule
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.calendar_screen.components.date.picker.MyDatePicker
import com.swirl.listifyplanner.presentation.common.RequiredField
import com.swirl.listifyplanner.presentation.common.TextIconButton
import com.swirl.listifyplanner.presentation.common.colorpicker.ColorPickedDisplay
import com.swirl.listifyplanner.presentation.common.colorpicker.ColorPickerDialog
import com.swirl.listifyplanner.ui.constants.DefaultIconSize
import com.swirl.listifyplanner.ui.constants.topAppBarTextStyle
import com.swirl.listifyplanner.ui.theme.DeepPurple700
import com.swirl.listifyplanner.ui.theme.TaskLightGreenBg
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord
import com.swirl.listifyplanner.utils.extenstions.dateToString
import com.swirl.listifyplanner.utils.getPurpleThemeTextFieldColors
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteToDateScreen(
    mainViewModel: MainViewModel,
    onBack: () -> Unit,
    chosenDate: LocalDate
) {
    val context = LocalContext.current
    var chosenColor by remember { mutableStateOf(Color.LightGray) }

    var showColorPickerDialog by rememberSaveable { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    var date by remember { mutableStateOf(LocalDate.now()) }

    var noteTitle by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var isValid by remember { mutableStateOf(false) }

    LaunchedEffect(chosenDate) {
        date = chosenDate
    }

    if (showDialog.value) {
        MyDatePicker(showDialog, onPositiveClick = { newDate ->
            date = newDate
            showDialog.value = false
        })
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = UiText.StringResource(R.string.calendar_add_note_title).asString(),
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = date.dateToString().capitalizeWord(),
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Icon(
                    imageVector = Icons.Rounded.CalendarMonth,
                    tint = DeepPurple700,
                    contentDescription = UiText.StringResource(R.string.icon_calendar).toString(),
                    modifier = Modifier
                        .size(DefaultIconSize)
                        .clickable { showDialog.value = true }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextIconButton(
                    text = UiText.StringResource(R.string.calendar_choose_color).asString(),
                    icon = Icons.Default.ColorLens,
                    contentDescription = UiText.StringResource(R.string.icon_color_lens).asString(),
                    tint = TaskLightGreenBg,
                    onClick = { showColorPickerDialog = true }
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .background(color = Color.Transparent)
                ) {
                    ColorPickedDisplay( color = chosenColor, borderColor = Color.LightGray)
                }
            }
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
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = noteTitle,
                onValueChange = {
                    noteTitle = it
                    isValid = it.isNotEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.small,
                        color = Color.DarkGray
                    ),
                placeholder = { Text(UiText.StringResource(R.string.calendar_note_title).asString()) },
                colors = getPurpleThemeTextFieldColors(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium,
                isError = !isValid
            )
            if (!isValid) { RequiredField() }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .border(
                        width = 1.dp,
                        shape = MaterialTheme.shapes.small,
                        color = Color.DarkGray
                    ),
                placeholder = { Text(UiText.StringResource(R.string.calendar_note_description).asString()) },
                colors = getPurpleThemeTextFieldColors(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                singleLine = false,
                minLines = 3,
                maxLines = 15,
                textStyle = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onClick = {
                    mainViewModel.insertCalendarNote(
                        date,
                        Note(
                            title = noteTitle,
                            description = description,
                            color = chosenColor,
                            timeStamp = LocalDateTime.now()
                        )
                    )
                    onBack()
                }
            ) {
                Text(
                    text = UiText.StringResource(R.string.calendar_note_save).asString(),
                    modifier = Modifier.padding(8.dp),
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddNoteToDateScreenPreview() {
    val context = LocalContext.current
    val database =  DatabaseModule.getDatabaseInstance(context)
    val todoDao: TodoDao = database.todoDao()
    val calendarNoteDao: CalendarNoteDao = database.calendarNoteDao()

    val todoRepository = TodoRepository(todoDao)
    val calendarNoteRepository = CalendarNoteRepository(calendarNoteDao)

    val mainViewModel = MainViewModel(
        todoRepository = todoRepository,
        calendarNoteRepository = calendarNoteRepository
    )

    AddNoteToDateScreen(
        mainViewModel = mainViewModel,
        onBack = {},
        chosenDate = LocalDate.now()
    )
}