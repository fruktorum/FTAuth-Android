package com.fruktorum.ftauth.customUI.registration

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import com.fruktorum.ftauth.FTAuth

class FTCheckBoxAcceptanceOfTerms(
    context: Context?,
    attrs: AttributeSet?
) : AppCompatCheckBox(context, attrs) {

    init {
        FTAuth.checkBoxAcceptanceOfTerms = this
    }

    fun set(checked: Boolean, image: Drawable) {
        this.isChecked = checked
        this.buttonDrawable = image
    }
}