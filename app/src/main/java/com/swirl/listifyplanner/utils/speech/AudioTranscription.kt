package com.swirl.listifyplanner.utils.speech

import android.speech.RecognitionListener

interface AudioTranscription {
    fun setRecognitionListener(listener: RecognitionListener?)
    fun startListening(languageCode: String)
    fun stopListening()
}