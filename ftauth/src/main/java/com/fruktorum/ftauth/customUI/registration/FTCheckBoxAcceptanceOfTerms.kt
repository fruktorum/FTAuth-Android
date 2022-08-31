package com.fruktorum.ftauth.customUI.registration

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.R
import com.fruktorum.ftauth.customUI.common.FTAuthUI

class FTCheckBoxAcceptanceOfTerms(
    context: Context,
    attrs: AttributeSet?
) : AppCompatCheckBox(context, attrs), FTAuthUI {

    private var errorTintList: ColorStateList? = null
    private var successTintList: ColorStateList? = null
    private var defaultTintList: ColorStateList? = null

    init {
        init(attrs)
    }

    override fun validate() {
        if (this.isChecked) setSuccessTint() else setErrorTint()
    }

    override fun onDetachedFromWindow() {
        this.setOnCheckedChangeListener(null)
        super.onDetachedFromWindow()
    }

    fun set(checked: Boolean, image: Drawable) {
        this.isChecked = checked
        this.buttonDrawable = image
        setSuccessTint()
    }

    private fun init(attrs: AttributeSet?) {
        FTAuth.checkBoxAcceptanceOfTerms = this

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FTCheckBoxField,
            0, 0
        ).apply {
            try {
                val errorColor = getResourceId(R.styleable.FTCheckBoxField_errorFieldColor, -1)
                val successColor = getResourceId(R.styleable.FTCheckBoxField_successFieldColor, -1)
                val defaultColor = getResourceId(R.styleable.FTCheckBoxField_defaultFieldColor, -1)

                errorTintList = ContextCompat.getColorStateList(
                    context,
                    if (errorColor == -1) R.color.colorError else errorColor
                )

                successTintList = ContextCompat.getColorStateList(
                    context,
                    if (successColor == -1) R.color.colorSuccess else successColor
                )

                defaultTintList = ContextCompat.getColorStateList(
                    context,
                    if (defaultColor == -1) R.color.colorSuccess else defaultColor
                )

                setDefaultTint()

            } finally {
                recycle()
            }
        }
        this.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setSuccessTint()
            } else {
                setErrorTint()
            }
        }
    }

    private fun setErrorTint() {
        buttonTintList = errorTintList
    }

    private fun setSuccessTint() {
        buttonTintList = successTintList
    }

    private fun setDefaultTint() {
        buttonTintList = defaultTintList
    }
}