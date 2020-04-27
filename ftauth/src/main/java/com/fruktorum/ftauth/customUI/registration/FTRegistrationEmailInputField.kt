package com.fruktorum.ftauth.customUI.registration

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.R
import com.fruktorum.ftauth.util.extensions.isEmailValid
import com.fruktorum.ftauth.util.extensions.setInputError
import com.fruktorum.ftauth.util.extensions.setInputSuccess
import com.fruktorum.ftauth.util.other.TextValidator
import kotlinx.android.synthetic.main.layout_email_input_field.view.*

class FTRegistrationEmailInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        init(attrs)
    }

    var isEmailValid = false

    val value: String
        get() {
            return edt_input_email.text.toString()
        }

    lateinit var description: TextView
    lateinit var inputField: EditText


    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_email_input_field, this)
        description = text_error_email_login
        inputField = edt_input_email
        edt_input_email.addTextChangedListener(object : TextValidator(edt_input_email) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                isEmailValid = validateEmail(textView, text)
            }
        })
        FTAuth.registerEmailInputField = this
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