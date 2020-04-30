package com.fruktorum.ftauth.customUI.registration

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.paris.extensions.style
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.R
import com.fruktorum.ftauth.util.extensions.setInputError
import com.fruktorum.ftauth.util.extensions.setInputSuccess
import com.fruktorum.ftauth.util.other.TextValidator
import kotlinx.android.synthetic.main.layout_password_input_field.view.*

class FTRegistrationPasswordInputField @JvmOverloads constructor(
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

    lateinit var description: TextView
    lateinit var inputField: EditText

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_password_input_field, this)
        description = text_error_password
        inputField = edt_input_password
        inputField.addTextChangedListener(object : TextValidator(inputField) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                isPasswordValid = validatePassword(textView, text)
            }
        })
        FTAuth.registerPasswordInputField = this
    }

    fun validatePassword(passwordField: TextView, password: String): Boolean {
        return if (password.length < 8) {
            passwordField.setInputError(
                text_error_password,
                context!!.getString(R.string.password_error),
                context!!
            )
            false
        } else {
            passwordField.setInputSuccess(text_error_password, context!!)
            true
        }
    }

    fun setInputFieldStyle(@StyleRes res: Int) {
        inputField.style(res)
    }

    fun setDescriptionStyle(@StyleRes res: Int) {
        description.style(res)
    }
}