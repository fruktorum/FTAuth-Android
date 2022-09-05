package com.fruktorum.ftauth.customUI.registration

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.paris.extensions.style
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.R
import com.fruktorum.ftauth.customUI.common.FTAuthUI
import com.fruktorum.ftauth.data.phoneNumber.PhoneMask
import com.fruktorum.ftauth.util.extensions.setInputError
import com.fruktorum.ftauth.util.extensions.setInputSuccess
import com.fruktorum.ftauth.util.other.TextValidator
import com.vicmikhailau.maskededittext.MaskedEditText

class FTRegistrationPhoneNumberInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), FTAuthUI {

    var isPhoneValid = false
    val value: String
        get() {
            return "${prefix}${inputField.unMaskedText}"
        }
    /** Must be public. It allows to apply the phone mask by user */
    var phoneMask: PhoneMask = PhoneMask.NONE
        set(value) {
            field = value
            setPhoneMaskToInputField(value)
        }

    /**
     * Must be public.
     * Prefix is intended to add special characters
     * before the entered numbers before sending to the server.
     * The value in the Prefix property is not taken during validation.
     */
    var prefix: String = ""

    /** Must be public. It allows to apply the user style for input field */
    lateinit var inputField: MaskedEditText
    /** Must be public. It allows to apply the user style for errors field */
    lateinit var description: TextView

    init {
        init(attrs)
        FTAuth.registerPhoneNumberInputField = this
    }

    override fun onDetachedFromWindow() {
        inputField.addTextChangedListener(null)
        super.onDetachedFromWindow()
    }

    override fun validate() {
        validatePhoneNumber(inputField)
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
        View.inflate(context, R.layout.layout_phone_number_input_field, this)
        description = findViewById(R.id.text_error_phone_number)
        inputField = findViewById(R.id.edt_input_phone_number)

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
                if (text == "+") return
                isPhoneValid = validatePhoneNumber(textView as MaskedEditText)
            }
        })
    }

    private fun setPhoneMaskToInputField(mask: PhoneMask) {
        when (mask) {
            is PhoneMask.CustomMask -> inputField.setMask(mask.mask.replace('X', '#'))
            PhoneMask.NONE -> inputField.setMask("*".repeat(50))
            PhoneMask.XX_XXX_XXX_XXXX -> inputField.setMask("+## (###) ###-####")
            PhoneMask.X_XXX_XXX_XXXX -> inputField.setMask("+# (###) ###-####")
            PhoneMask.PLUS -> inputField.setMask("+###########")
        }
    }

    private fun validatePhoneNumber(phoneField: MaskedEditText): Boolean {
        return when (phoneMask) {
            PhoneMask.NONE -> true
            is PhoneMask.CustomMask -> {
                val charsCount = (phoneMask as PhoneMask.CustomMask).mask.count { it == '#' }
                checkPhoneNumberSize(phoneField, charsCount)
            }
            PhoneMask.XX_XXX_XXX_XXXX -> checkPhoneNumberSize(phoneField, 12)
            PhoneMask.X_XXX_XXX_XXXX -> checkPhoneNumberSize(phoneField, 11)
            PhoneMask.PLUS -> checkPhoneNumberSize(phoneField, 11)
        }
    }

    private fun checkPhoneNumberSize(phoneField: MaskedEditText, requiredSize: Int): Boolean {
        return if ((phoneField.unMaskedText?.length ?: 0) < requiredSize) {
            phoneField.setInputError(
                description,
                context.getString(R.string.ft_auth_phone_number_error),
                context
            )
            false
        } else {
            phoneField.setInputSuccess(
                description,
                context
            )
            true
        }
    }
}