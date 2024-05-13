package com.swirl.listifyplanner.presentation.speech_to_text_screen

import TitleTopAppBar
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.MainViewModel
import com.swirl.listifyplanner.presentation.alert_dialogs.AlertDialogAddScreen
import com.swirl.listifyplanner.presentation.common.DraggableComponent
import com.swirl.listifyplanner.presentation.common.drawAnimationBorder
import com.swirl.listifyplanner.presentation.common.toastMsg
import com.swirl.listifyplanner.presentation.speech_to_text_screen.components.TextFieldMenu
import com.swirl.listifyplanner.ui.constants.DefaultDp
import com.swirl.listifyplanner.ui.constants.LargeDp
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord
import com.swirl.listifyplanner.utils.speech.SpeechToTextConverter
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
@Composable
fun SpeechToTextScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val recordAudioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)


    val localLanguage = Locale.getDefault()
    val language: String = localLanguage.getDisplayName(localLanguage)
    val defaultLanguage = language to localLanguage.country
    var chosenLanguage by rememberSaveable { mutableStateOf(defaultLanguage) }
    val optionsFocusRequester = remember { FocusRequester() }

    val speechToTextConverter = remember(context) { SpeechToTextConverter(context) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            speechToTextConverter.startListening(chosenLanguage.second)
        } else {
            toastMsg(context, UiText.StringResource(R.string.speech_permission_prompt).asString(context), true)
        }
    }

    val state by speechToTextConverter.state.collectAsState()
    var openDialog by rememberSaveable { mutableStateOf(false) }

    val isoLanguages = Locale.getISOLanguages()
    val languages = isoLanguages.map { languageCode ->
        val locale = Locale(languageCode)
        val displayName = locale.getDisplayName(locale)
        val bcp47 = locale.toLanguageTag()
        displayName to bcp47
    }

    val sortedLanguages = languages
        .groupBy { it.first.firstOrNull()?.uppercaseChar() }
        .toSortedMap(compareBy { it ?: ' ' })
        .flatMap { (_, list) -> list.sortedBy { it.first } }

    LaunchedEffect(state) {
        if (state.error != null) {
            val errorCode: Int = state.error ?: -1
            toastMsg(context, errorCode.getRecognitionListenerErrorType())
        }
    }

    Scaffold(
        topBar = { TitleTopAppBar(title = UiText.StringResource(R.string.speech_top_title).asString()) },
        floatingActionButton = {
            if (state.spokenText.isNotEmpty()) {
                DraggableComponent {
                    FloatingActionButton(
                        shape = RoundedCornerShape(percent = 100),
                        onClick = { openDialog = true }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = UiText.StringResource(R.string.icon_add).asString()
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        AlertDialogAddScreen(
            openDialog = openDialog,
            onClose = { openDialog = false },
            mainViewModel = mainViewModel,
            state.spokenText.takeUnless { it.isEmpty() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LargeDp)
            ) {
                /* OutlinedTextField(
                     value = chosenLanguage,
                     onValueChange = { chosenLanguage = it },
                     modifier = Modifier
                         .fillMaxWidth()
                         .onGloballyPositioned { coordinates ->
                             //This value is used to assign to the DropDown the same width
                             textfieldSize = coordinates.size.toSize()
                         },
                     label = {Text("Choose language")},
                     trailingIcon = {
                         Icon(
                             icon,
                             "contentDescription",
                             Modifier.clickable { expanded = !expanded }
                         )
                     }
                 )
                 Box(modifier = Modifier.padding(end = 8.dp)) {
                     TextButton(
                         onClick = { expanded = true },
                         modifier = Modifier.padding(8.dp)
                     ) {
                         Text(text = languages.find { it.second == chosenLanguage }?.first?.capitalizeWord() ?: "")
                     }
                     DropdownMenu(
                         expanded = expanded,
                         onDismissRequest = { expanded = false }
                     ) {
                         sortedLanguages.forEach { (language, languageCode) ->
                             DropdownMenuItem(
                                 onClick = {
                                     chosenLanguage = languageCode
                                     expanded = false
                                 }
                             ) {
                                 Text(text = language.capitalizeWord())
                             }
                         }
                     }
                 }*/

                Column(
                    modifier = Modifier
                        .imePadding()
                        .verticalScroll(rememberScrollState())
                        .padding(DefaultDp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextFieldMenu(
                        label = "Chosen Language",
                        options = sortedLanguages,
                        selectedOption = sortedLanguages.find { it == chosenLanguage },
                        onOptionSelected = { chosenLanguage = it ?: defaultLanguage },
                        optionToString = { it.first.capitalizeWord() },
                        filteredOption = { searchInput ->
                            sortedLanguages.filter {
                                it.toString().contains(searchInput, ignoreCase = true)
                            }
                        },
                        focusRequester = optionsFocusRequester
                    )
                }
            }

            Button(
                onClick = {
                    if (state.isSpeaking) {
                        speechToTextConverter.stopListening()
                    } else {
                        if (recordAudioPermissionState.status.isGranted) {
                            speechToTextConverter.startListening(chosenLanguage.second)
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .padding(16.dp)
                    .drawAnimationBorder(
                        strokeWidth = if (state.isSpeaking) 15.dp else 0.dp,
                        durationMillis = 3500,
                        shape = RoundedCornerShape(100)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (state.isSpeaking) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = UiText.StringResource(R.string.icon_mic).asString(),
                        modifier = Modifier
                            .weight(4f)
                            .fillMaxSize()
                            .padding(6.dp),
                        tint = Color.LightGray
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontSize = 10.sp,
                        text = if (state.isSpeaking) {
                            UiText.StringResource(R.string.speech_stop).asString()
                        } else {
                            UiText.StringResource(R.string.speech_start).asString()
                        },
                        color = Color.LightGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .clip(shape = RoundedCornerShape(16.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .defaultMinSize(200.dp, 60.dp)
                        .border(width = 4.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.spokenText,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
