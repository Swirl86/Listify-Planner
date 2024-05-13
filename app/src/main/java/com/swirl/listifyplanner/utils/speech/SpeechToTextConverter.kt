package com.swirl.listifyplanner.utils.speech

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SpeechToTextConverter(context: Context) : AudioTranscription {

    private val _state = MutableStateFlow(SpeechToTextConverterState())
    val state = _state.asStateFlow()

    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    private val recognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            _state.update {
                it.copy(error = null)
            }
        }
        override fun onBeginningOfSpeech() = Unit
        override fun onRmsChanged(rmsdB: Float) = Unit
        override fun onBufferReceived(buffer: ByteArray?) = Unit
        override fun onEndOfSpeech() {
            _state.update {
                it.copy(isSpeaking = false)
            }
        }
        override fun onError(error: Int) {
            if (error == SpeechRecognizer.ERROR_CLIENT) return

            _state.update {
                it.copy(error = error)
            }
        }
        override fun onResults(results: Bundle?) {
            results
                ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.getOrNull(0)
                ?.let { result ->
                    _state.update {
                        it.copy(spokenText = result)
                    }
                }
        }
        override fun onPartialResults(partialResults: Bundle?) = Unit
        override fun onEvent(eventType: Int, params: Bundle?) = Unit
    }

    init {
        _state.update { SpeechToTextConverterState() }
        setRecognitionListener(recognitionListener)
    }

    override fun setRecognitionListener(listener: RecognitionListener?) {
        speechRecognizer.setRecognitionListener(listener)
    }

    override fun startListening(languageCode: String) {
        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE, languageCode)
            .putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 3000L)

        speechRecognizer.startListening(recognizerIntent)
        _state.update {
            it.copy(
                spokenText = "",
                isSpeaking = true
            )
        }
    }

    override fun stopListening() {
        _state.update {
            it.copy(isSpeaking = false)
        }
        speechRecognizer.stopListening()
    }
}