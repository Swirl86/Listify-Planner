package com.swirl.listifyplanner.utils.extenstions

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDateTime.getOutPutString(): String {
    val pattern = "dd MMM yyyy HH:mm"
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDateTime.yearRange(nr: Int = 10) = (this.minusYears(nr.toLong()).year..this.plusYears(nr.toLong()).year)