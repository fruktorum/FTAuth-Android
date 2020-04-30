package com.fruktorum.ftauth.customUI.registration

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.fruktorum.ftauth.FTAuth

class FTRegistrationButton(
    context: Context?,
    attrs: AttributeSet?
) : AppCompatButton(context, attrs) {

    init {
        setOnClickListener {
            FTAuth.getInstance().registration()
        }
    }
}