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
import com.fruktorum.ftauth.customUI.common.FTAuthUI
import com.fruktorum.ftauth.util.extensions.setInputError
import com.fruktorum.ftauth.util.extensions.setInputSuccess
import com.fruktorum.ftauth.util.other.TextValidator

class FTRegistrationLastNameInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), FTAuthUI {

    var isLastNameValid = false
    val value: String
        get() {
            return inputField.text.toString()
        }

    /** Must be public. It allows to apply the user style for input field */
    lateinit var inputField: EditText
    /** Must be public. It allows to apply the user style for errors field */
    lateinit var description: TextView

    init {
        init(attrs)
        FTAuth.registerLastNameInputField = this
    }

    override fun onDetachedFromWindow() {
        inputField.addTextChangedListener(null)
        super.onDetachedFromWindow()
    }

    override fun validate() {
        validateLastName(inputField, inputField.text.toString())
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
        View.inflate(context, R.layout.layout_last_name_input_field, this)
        description = findViewById(R.id.text_error_last_name)
        inputField = findViewById(R.id.edt_input_last_name)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FTAuthInputField,
            0, 0
        ).apply {
            try {
                val inputStyle = getResourceId(R.styleable.FTAuthInputField_inputFieldStyle, -1)
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
                isLastNameValid = validateLastName(textView, text)
            }
        })
    }

    private fun validateLastName(nameField: TextView, lastName: String): Boolean {
        return if (lastName.isEmpty()) {
            nameField.setInputError(
                description,
                context!!.getString(R.string.ft_auth_last_name_error),
                context!!
            )
            false
        } else {
            nameField.setInputSuccess(description, context!!)
            true
        }
    }
}