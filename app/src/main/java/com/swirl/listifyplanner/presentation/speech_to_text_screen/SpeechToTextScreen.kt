package com.swirl.listifyplanner.presentation.speech_to_text_screen

import TitleTopAppBar
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.speech.SpeechToTextConverter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SpeechToTextScreen(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val recordAudioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val speechToTextConverter = remember(context) { SpeechToTextConverter(context) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            speechToTextConverter.startListening()
        } else {
            toastMsg(context, UiText.StringResource(R.string.speech_permission_prompt).asString(context), true)
        }
    }

    val state by speechToTextConverter.state.collectAsState()
    var openDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (!state.error.isNullOrEmpty())
            toastMsg(context, state.error!!)
    }

    Scaffold(
        topBar = { TitleTopAppBar(title = UiText.StringResource(R.string.speech_top_title).asString(context)) },
        floatingActionButton = {
            if (state.spokenText.isNotEmpty()) {
                DraggableComponent {
                    FloatingActionButton(
                        shape = RoundedCornerShape(percent = 100),
                        onClick = { openDialog = true }
                    ) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
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
            Button(
                onClick = {
                    if (state.isSpeaking) {
                        speechToTextConverter.stopListening()
                    } else {
                        if (recordAudioPermissionState.status.isGranted) {
                            speechToTextConverter.startListening()
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
                        contentDescription = null,
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
                            UiText.StringResource(R.string.speech_stop).asString(context)
                        } else {
                            UiText.StringResource(R.string.speech_start).asString(context)
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
