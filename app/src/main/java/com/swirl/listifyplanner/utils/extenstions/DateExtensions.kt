package com.swirl.listifyplanner.utils.extenstions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.getOutPutString(): String {
    val pattern = "dd MMM yyyy HH:mm"
    return this.format(DateTimeFormatter.ofPattern(pattern))
}