package com.swirl.listifyplanner.utils.extenstions

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.dateTimeToString(): String {
    val pattern = "dd MMM yyyy HH:mm"
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun LocalDateTime.yearRange(nr: Int = 10) = (this.minusYears(nr.toLong()).year..this.plusYears(nr.toLong()).year)

fun Long.convertMillisToLocalDate() : LocalDate {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun convertMillisToLocalDateWithFormatter(date: LocalDate, dateTimeFormatter: DateTimeFormatter) : LocalDate {
    val dateInMillis = LocalDate.parse(date.format(dateTimeFormatter), dateTimeFormatter)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    return dateInMillis.convertMillisToLocalDate()
}

fun LocalDate.dateToString(): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", Locale.getDefault())
    val dateInMillis = convertMillisToLocalDateWithFormatter(this, dateFormatter)
    return dateFormatter.format(dateInMillis)
}