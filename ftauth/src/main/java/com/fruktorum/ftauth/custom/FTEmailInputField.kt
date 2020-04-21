package com.fruktorum.ftauth.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fruktorum.ftauth.R
import com.fruktorum.ftauth.util.TextValidator
import com.fruktorum.ftauth.util.isEmailValid
import com.fruktorum.ftauth.util.setInputError
import com.fruktorum.ftauth.util.setInputSuccess
import kotlinx.android.synthetic.main.layout_email_input_field.view.*

class FTEmailInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    var isEmailValid = false

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_email_input_field, this)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.FTEmailInputField)
        val emailHint = ta.getString(R.styleable.FTEmailInputField_android_hint)
        if (emailHint != null) edt_input_email.hint = emailHint
        edt_input_email.addTextChangedListener(object : TextValidator(edt_input_email) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                isEmailValid = validateEmail(textView, text)
            }
        })

        ta.recycle()
    }

    fun validateEmail(emailField: TextView, email: String): Boolean {
        return if (!email.isEmailValid() or email.isEmpty()) {
            emailField.setInputError(
                text_error_email_login,
                context!!.getString(R.string.email_error),
                context!!
            )
            false
        } else {
            emailField.setInputSuccess(text_error_email_login, context!!)
            true
        }
    }
}