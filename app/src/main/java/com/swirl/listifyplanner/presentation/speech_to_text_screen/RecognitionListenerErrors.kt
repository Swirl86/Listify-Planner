package com.swirl.listifyplanner.presentation.speech_to_text_screen

//https://developer.android.com/reference/android/speech/SpeechRecognizer#ERROR_AUDIO

/**
 * Some of the most common errors and what went wrong
 */
fun Int.getRecognitionListenerErrorType(): String {
    return when (this) {
        1, 2 -> ErrorType.ERROR_NETWORK.message
        5 -> ErrorType.ERROR_CLIENT.message
        7 -> ErrorType.ERROR_NO_MATCH.message
        8 -> ErrorType.ERROR_RECOGNIZER_BUSY.message
        9 -> ErrorType.ERROR_INSUFFICIENT_PERMISSIONS.message
        12 -> ErrorType.ERROR_LANGUAGE_NOT_SUPPORTED.message
        13 -> ErrorType.ERROR_LANGUAGE_UNAVAILABLE.message
        else -> ErrorType.UNKNOWN.message
    }
}

enum class ErrorType(val message: String) {
    ERROR_NETWORK("Network Error"),
    ERROR_CLIENT("Functionality may not be supported if Android version is below 12"),
    ERROR_NO_MATCH("No speech was recognized"),
    ERROR_RECOGNIZER_BUSY("Already listening"),
    ERROR_INSUFFICIENT_PERMISSIONS("Insufficient permissions, need microphone permission!"),
    ERROR_LANGUAGE_NOT_SUPPORTED("The requested language is not supported"),
    ERROR_LANGUAGE_UNAVAILABLE("The requested language is supported but not available"),
    UNKNOWN("Something went wrong!")
}