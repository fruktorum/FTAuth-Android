package com.fruktorum.ftauth.customUI.webView

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.fruktorum.ftauth.FTAuth

class FTLoginFacebookButton(
    context: Context,
    attrs: AttributeSet?
) : AppCompatButton(context, attrs) {

    init {
        setOnClickListener {
            FTAuth.getInstance().loginByFacebook(it.context)
        }
    }
}