package com.fruktorum.ftauthsample.ui.base

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {

    fun showProgress()
    fun hideProgress()
}