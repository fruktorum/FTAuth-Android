package com.fruktorum.ftauthsample.util.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.fruktorum.ftauthsample.ui.main.MainActivity

fun Fragment.hideKeyboard() {
    val activity = this.activity as MainActivity

    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null)
        view = View(activity)

    imm.hideSoftInputFromWindow(view.windowToken, 0)
}