package com.fruktorum.ftauth.customUI.registration

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import com.fruktorum.ftauth.FTAuth
import com.fruktorum.ftauth.customUI.common.FTAuthUI

class FTCheckBoxAcceptanceOfTerms(
    context: Context?,
    attrs: AttributeSet?
) : AppCompatCheckBox(context, attrs), FTAuthUI {

    init {
        FTAuth.checkBoxAcceptanceOfTerms = this
    }

    fun set(checked: Boolean, image: Drawable) {
        this.isChecked = checked
        this.buttonDrawable = image
    }

    override fun validate() {
        //TODO: Make validation
    }
}