package com.fruktorum.ftauth.customUI.auth

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
import com.fruktorum.ftauth.customUI.common.FTAuthUI
import com.fruktorum.ftauth.util.extensions.isEmailValid
import com.fruktorum.ftauth.util.extensions.setInputError
import com.fruktorum.ftauth.util.extensions.setInputSuccess
import com.fruktorum.ftauth.util.other.TextValidator

class FTAuthEmailInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), FTAuthUI {

    var isEmailValid = false
    val value: String
        get() {
            return inputField.text.toString()
        }

    /** Must be public. It allows to apply the user style for errors field */
    lateinit var description: TextView
    /** Must be public. It allows to apply the user style for input field */
    lateinit var inputField: EditText

    init {
        init(attrs)
        FTAuth.authEmailInputField = this
    }

    override fun onDetachedFromWindow() {
        inputField.addTextChangedListener(null)
        super.onDetachedFromWindow()
    }

    override fun validate() {
        validateEmail(inputField, inputField.text.toString())
    }

    override fun setErrorMessage(message: String) {
        inputField.setInputError(
            description,
            message,
            context!!
        )
    }

    /** Must be public. It allows to apply the user style for input field */
    fun setInputFieldStyle(@StyleRes res: Int) {
        inputField.style(res)
    }

    /** Must be public. It allows to apply the user style for errors field */
    fun setDescriptionStyle(@StyleRes res: Int) {
        description.style(res)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_email_input_field, this)
        description = findViewById(R.id.text_error_email)
        inputField = findViewById(R.id.edt_input_email)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FTAuthInputField,
            0, 0
        ).apply {
            try {
                val inputStyle =
                    getResourceId(R.styleable.FTAuthInputField_inputFieldStyle, -1)
                if (inputStyle != -1) setInputFieldStyle(inputStyle)

                val descriptionStyle =
                    getResourceId(R.styleable.FTAuthInputField_descriptionStyle, -1)
                if (descriptionStyle != -1) setDescriptionStyle(descriptionStyle)
            } finally {
                recycle()
            }
        }

        inputField.addTextChangedListener(object : TextValidator(inputField) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                isEmailValid = validateEmail(textView, text.trim())
            }
        })
    }

    private fun validateEmail(emailField: TextView, email: String): Boolean {
        return if (!email.isEmailValid() or email.isEmpty()) {
            emailField.setInputError(
                description,
                context!!.getString(R.string.ft_auth_email_error),
                context!!
            )
            false
        } else {
            emailField.setInputSuccess(description, context!!)
            true
        }
    }
}