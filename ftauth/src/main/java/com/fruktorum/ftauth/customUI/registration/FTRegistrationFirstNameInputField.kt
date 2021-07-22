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

class FTRegistrationFirstNameInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), FTAuthUI {

    init {
        init(attrs)
    }

    var isFirstNameValid = false

    val value: String
        get() {
            return inputField.text.toString()
        }

    lateinit var description: TextView
    lateinit var inputField: EditText


    init {
        FTAuth.registerFirstNameInputField = this
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_first_name_input_field, this)
        description = findViewById(R.id.text_error_first_name)
        inputField = findViewById(R.id.edt_input_first_name)
        inputField.addTextChangedListener(object : TextValidator(inputField) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                isFirstNameValid = validateName(textView, text)
            }
        })

    }

    override fun validate() {
        validateName(inputField, inputField.text.toString())
    }

    fun validateName(nameField: TextView, name: String): Boolean {
        return if (name.isEmpty()) {
            nameField.setInputError(
                description,
                context!!.getString(R.string.ft_auth_first_name_error),
                context!!
            )
            false
        } else {
            nameField.setInputSuccess(description, context!!)
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