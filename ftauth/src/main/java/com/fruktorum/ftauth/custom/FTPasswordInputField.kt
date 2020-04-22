package com.fruktorum.ftauth.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fruktorum.ftauth.R
import com.fruktorum.ftauth.util.extensions.setInputError
import com.fruktorum.ftauth.util.extensions.setInputSuccess
import com.fruktorum.ftauth.util.other.TextValidator
import kotlinx.android.synthetic.main.layout_password_input_field.view.*

class FTPasswordInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    var isPasswordValid = false

    val value: String
        get() {
            return edt_input_password.text.toString()
        }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_password_input_field, this)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FTPasswordInputField)

        edt_input_password.addTextChangedListener(object : TextValidator(edt_input_password) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                isPasswordValid = validatePassword(textView, text)
            }
        })

        ta.recycle()
    }

    fun validatePassword(passwordField: TextView, password: String): Boolean {
        return if (password.length < 8) {
            passwordField.setInputError(
                text_error_password_login,
                context!!.getString(R.string.password_error),
                context!!
            )
            false
        } else {
            passwordField.setInputSuccess(text_error_password_login, context!!)
            true
        }
    }
}