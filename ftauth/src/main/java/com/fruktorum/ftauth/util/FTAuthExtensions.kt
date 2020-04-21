package com.fruktorum.ftauth.util

import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fruktorum.ftauth.R


fun String.isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun TextView.setInputError(textViewError: TextView, errorMsg: String, context: Context) {
    this.setTextColor(ContextCompat.getColor(context, R.color.colorError))
    textViewError.text = errorMsg
}

fun TextView.setInputSuccess(textViewError: TextView, context: Context) {
    this.setTextColor(ContextCompat.getColor(context, R.color.colorSuccess))
    textViewError.text = ""
}

fun TextView.isFieldEmpty(): Boolean = this.text.toString().isEmpty()
fun EditText.isFieldEmpty(): Boolean = this.text.toString().isEmpty()

