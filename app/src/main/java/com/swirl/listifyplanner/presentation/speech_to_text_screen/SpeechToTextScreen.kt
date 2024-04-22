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
import com.swirl.listifyplanner.utils.speech.SpeechToTextConverter

@Composable
fun SpeechToTextScreen() {
    val context = LocalContext.current
    var result by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }

    val speechToTextConverter = remember(context) {
        SpeechToTextConverter(context = context) { transcribedText ->
            result = transcribedText
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            isListening = true
            speechToTextConverter.startListening()
        }
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
                } else {
                    result = ""
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        speechToTextConverter.startListening()
                    } else {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }
                }
                isListening = !isListening
            }
        ) {
            Text(
                text = if (isListening) "Stop Speech Recording" else "Start Speech Recording",
                color = if (isListening) Color.Red else Color.Green
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = result.ifEmpty { "Result will be shown here" },
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        )
    }
}
