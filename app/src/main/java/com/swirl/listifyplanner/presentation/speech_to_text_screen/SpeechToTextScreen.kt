package com.swirl.listifyplanner.presentation.speech_to_text_screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.swirl.listifyplanner.R
import com.swirl.listifyplanner.utils.UiText
import com.swirl.listifyplanner.utils.speech.SpeechToTextConverter

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SpeechToTextScreen() {
    val context = LocalContext.current
    val recordAudioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    var result by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }

    val speechToTextConverter = remember(context) {
        SpeechToTextConverter(context = context) { transcribedText ->
            result = transcribedText
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            speechToTextConverter.startListening()
            isListening = true
        } else {
            // Handle permission denial
        }
    }

    LaunchedEffect (!recordAudioPermissionState.status.isGranted) {
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (isListening) {
                    speechToTextConverter.stopListening()
                    isListening = false
                } else {
                    result = ""
                    if (recordAudioPermissionState.status.isGranted) {
                        speechToTextConverter.startListening()
                        isListening = true
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
            }
        ) {
            Text(
                text = if (isListening) {
                    UiText.StringResource(R.string.speech_stop).asString(context)
                } else {
                    UiText.StringResource(R.string.speech_start).asString(context)
                },
                color = if (isListening) Color.Red else Color.Green
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result.ifEmpty { UiText.StringResource(R.string.speech_result_shown).asString(context) },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        )
    }
}
