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
import kotlinx.android.synthetic.main.layout_phone_number_input_field.view.*

class FTRegistrationPhoneNumberInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), FTAuthUI {

    init {
        init(attrs)
    }

    var prefix: String = ""
    var isPhoneValid = false

    val value: String
        get() {
            return "${prefix}${inputField.unMaskedText}"
        }

    lateinit var description: TextView
    lateinit var inputField: MaskedEditText

    var phoneMask: PhoneMask = PhoneMask.NONE
        set(value) {
            field = value
            setPhoneMaskToInputField(value)
        }


    private fun setPhoneMaskToInputField(mask: PhoneMask) {
        when (mask) {
            is PhoneMask.CustomMask -> inputField.setMask(mask.mask)
            PhoneMask.NONE -> inputField.setMask("*".repeat(50))
            PhoneMask.XX_XXX_XXX_XXXX -> inputField.setMask("+## (###) ###-####")
            PhoneMask.X_XXX_XXX_XXXX -> inputField.setMask("+# (###) ###-####")
        }
    }

    init {
        FTAuth.registerPhoneNumberInputField = this
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.layout_phone_number_input_field, this)
        description = text_error_phone_number
        inputField = edt_input_phone_number
        inputField.addTextChangedListener(object : TextValidator(edt_input_phone_number) {
            override fun validate(
                textView: TextView,
                text: String
            ) {
                if (text == "+") return
                isPhoneValid = validatePhoneNumber(textView as MaskedEditText)
            }
        })
    }

    override fun validate() {
        validatePhoneNumber(inputField)
    }

    fun validatePhoneNumber(phoneField: MaskedEditText): Boolean {
        return when (phoneMask) {
            PhoneMask.NONE -> true
            is PhoneMask.CustomMask -> {
                val charsCount = (phoneMask as PhoneMask.CustomMask).mask.count { it == '#' }
                checkPhoneNumberSize(phoneField, charsCount)
            }
            PhoneMask.XX_XXX_XXX_XXXX -> checkPhoneNumberSize(phoneField, 12)
            PhoneMask.X_XXX_XXX_XXXX -> checkPhoneNumberSize(phoneField, 11)
        }
    }

    private fun checkPhoneNumberSize(phoneField: MaskedEditText, requiredSize: Int): Boolean {
        return if (phoneField.unMaskedText?.length ?: 0 < requiredSize) {
            phoneField.setInputError(
                text_error_phone_number,
                context.getString(R.string.ft_auth_phone_number_error),
                context
            )
            false
        } else {
            phoneField.setInputSuccess(
                text_error_phone_number,
                context
            )
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