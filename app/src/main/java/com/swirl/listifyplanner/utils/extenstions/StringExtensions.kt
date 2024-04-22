package com.swirl.listifyplanner.utils.extenstions

import java.util.Locale

fun String.capitalizeWord(): String =
    this.split(" ").joinToString(" ") {
        it.replaceFirstChar { char ->
            if (char.isLowerCase())
                char.titlecase(Locale.getDefault())
            else char.toString()
        }
    }