package com.swirl.listifyplanner.utils.speech

import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.swirl.listifyplanner.presentation.common.toastMsg

class SpeechToTextConverter(
    private val context: Context,
    private val listener: (String) -> Unit
) : AudioTranscription {

    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
    private val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

    private var onSpeechStart: (() -> Unit)? = null

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) = Unit
        override fun onBeginningOfSpeech() {
            onSpeechStart?.invoke()
        }
        override fun onRmsChanged(rmsdB: Float) = Unit
        override fun onBufferReceived(buffer: ByteArray?) = Unit
        override fun onEndOfSpeech() = Unit
        override fun onError(error: Int) {
            when(error) {
                SpeechRecognizer.ERROR_NO_MATCH,
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> toastMsg(context, "No match or timeout : $error")
                else -> toastMsg(context, "Something went wrong : $error")
            }
        }
        override fun onResults(results: Bundle?) {
            results
                ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.getOrNull(0)
                ?.let {
                    listener.invoke(it)
                } ?: toastMsg(context, "Got no text to work with")
        }
        override fun onPartialResults(partialResults: Bundle?) = Unit
        override fun onEvent(eventType: Int, params: Bundle?) = Unit
    }

    init {
        setRecognitionListener(recognitionListener)
    }

    fun setOnSpeechStart(action: () -> Unit) {
        onSpeechStart = action // Clear result, get an empty start
    }

    override fun setRecognitionListener(listener: RecognitionListener?) {
        speechRecognizer.setRecognitionListener(listener)
    }

    override fun startListening() {
        speechRecognizer.startListening(recognizerIntent)
    }

    override fun stopListening() {
        speechRecognizer.stopListening()
    }
}