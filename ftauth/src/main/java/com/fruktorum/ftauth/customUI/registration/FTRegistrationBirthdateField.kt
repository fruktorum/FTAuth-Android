package com.fruktorum.ftauth.customUI.registration

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Build
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
import java.text.SimpleDateFormat
import java.util.*


class FTRegistrationBirthdateField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr), FTAuthUI {

    var isBirthdateValid = false

    val value: String
        get() {
            return inputField.text.toString()
        }

    /** Must be public. It allows to apply the user style for input field */
    lateinit var inputField: EditText

    /** Must be public. It allows to apply the user style for errors field */
    lateinit var description: TextView

    private val calendar = Calendar.getInstance()

    private val dateListener =
        OnDateSetListener { view, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateLabel()
        }

    init {
        init(attrs)
        FTAuth.registerBirthdateInputField = this
    }

    override fun onDetachedFromWindow() {
        inputField.addTextChangedListener(null)
        super.onDetachedFromWindow()
    }

    override fun validate() {
        isBirthdateValid = validateBirthdate(inputField, inputField.text.toString())
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
        View.inflate(context, R.layout.layout_birthdate_input_field, this)
        description = findViewById(R.id.text_error_birthdate)
        inputField = findViewById(R.id.edt_input_birthdate)

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

        inputField.isFocusable = false
        inputField.isFocusableInTouchMode = false

        inputField.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DatePickerDialog(
                    this.context,
                    dateListener,
                    calendar[Calendar.YEAR],
                    calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                ).show()
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        inputField.setText(dateFormat.format(calendar.time))
    }

    private fun validateBirthdate(birthdateField: TextView, birthdate: String): Boolean {
        return if (birthdate.isEmpty()) {
            birthdateField.setInputError(
                description,
                context!!.getString(R.string.ft_auth_birthdate_error),
                context!!
            )
            false
        } else {
            birthdateField.setInputSuccess(description, context!!)
            true
        }
    }
}