package com.swirl.listifyplanner.utils.speech

data class SpeechToTextConverterState(
    val spokenText: String = "",
    val isSpeaking: Boolean = false,
    val error: Int? = null
)
