package com.swirl.listifyplanner.presentation.common

import android.content.Context
import android.widget.Toast

fun toastMsg(
    context: Context,
    msg: String,
    lengthLong: Boolean = false
) {
    Toast.makeText(
        context,
        msg,
        if (lengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}