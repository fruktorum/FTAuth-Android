package com.fruktorum.ftauth.util.extensions

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fruktorum.ftauth.R


internal fun String.isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()


internal fun TextView.setInputError(textViewError: TextView, errorMsg: String, context: Context) {
    this.setTextColor(ContextCompat.getColor(context, R.color.colorError))
    textViewError.text = errorMsg
}

internal fun TextView.setInputSuccess(textViewError: TextView, context: Context) {
    this.setTextColor(ContextCompat.getColor(context, R.color.colorSuccess))
    textViewError.text = ""
}

internal fun TextView.isFieldEmpty(): Boolean = this.text.toString().isEmpty()
internal fun EditText.isFieldEmpty(): Boolean = this.text.toString().isEmpty()

