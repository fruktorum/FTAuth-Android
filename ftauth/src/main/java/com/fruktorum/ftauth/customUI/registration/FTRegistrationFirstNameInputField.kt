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
import kotlinx.android.synthetic.main.layout_first_name_input_field.view.*

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
            return edt_input_first_name.text.toString()
        }

    lateinit var description: TextView
    lateinit var inputField: EditText


    init {
        FTAuth.registerFirstNameInputField = this
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_first_name_input_field, this)
        description = text_error_first_name
        inputField = edt_input_first_name
        inputField.addTextChangedListener(object : TextValidator(edt_input_first_name) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                isFirstNameValid = validateName(textView, text)
            }
        })

    }

    override fun validate() {
        validateName(edt_input_first_name, edt_input_first_name.text.toString())
    }

    fun validateName(nameField: TextView, name: String): Boolean {
        return if (name.isEmpty()) {
            nameField.setInputError(
                text_error_first_name,
                context!!.getString(R.string.ft_auth_first_name_error),
                context!!
            )
            false
        } else {
            nameField.setInputSuccess(text_error_first_name, context!!)
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