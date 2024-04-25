package com.swirl.listifyplanner.presentation.speech_to_text_screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.presentation.common.toastMsg
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.speech.SpeechToTextConverter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SpeechToTextScreen() {
    val context = LocalContext.current
    val recordAudioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val speechToTextConverter = remember(context) {
        SpeechToTextConverter(context)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            speechToTextConverter.startListening()
        } else {
            toastMsg(context, UiText.StringResource(R.string.speech_permission_prompt).asString(context), true)
        }
    }

    LaunchedEffect (!recordAudioPermissionState.status.isGranted) {
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    val state by speechToTextConverter.state.collectAsState()

    LaunchedEffect(state) {
        if (!state.error.isNullOrEmpty())
            toastMsg(context, state.error!!)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
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
            }
        ) {
            Text(
                text = if (state.isSpeaking) {
                    UiText.StringResource(R.string.speech_stop).asString(context)
                } else {
                    UiText.StringResource(R.string.speech_start).asString(context)
                },
                color = if (state.isSpeaking) Color.Red else Color.Green
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = state.spokenText.ifEmpty { UiText.StringResource(R.string.speech_result_shown).asString(context) },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        )
    }
}
